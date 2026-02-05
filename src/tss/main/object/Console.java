package tss.main.object;

import scope.internal.facade.Access.ScopeEngineAccess;
import tss.main.Main;

import java.awt.*;
import java.util.ArrayList;

public class Console {
    private boolean isOpen = false;
    private StringBuilder buffer = new StringBuilder();
    private ArrayList<String> logs = new ArrayList<>();

    // 스크롤 처리를 위한 변수 (로그가 많아지면 위로 밀리게)
    private final int MAX_LINES = 10;

    ScopeEngineAccess scopeEngine;
    Main main;

    public Console(ScopeEngineAccess sc, Main main) {
        scopeEngine = sc;
        this.main = main;
    }

    public boolean isOpen() { return isOpen; }

    public void toggle() {
        isOpen = !isOpen;
    }

    // ★ 핵심: 키보드 입력을 받아먹는 함수
    public void inputKey(char key, int code) {
        if (!isOpen) return;

        if (code == 10) { // Enter Key Code
            execute();
        } else if (code == 8) { // Backspace
            if (buffer.length() > 0) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
        } else {
            // 특수문자나 이상한 거 제외하고 출력 가능한 것만 받음
            // (자바 char는 유니코드라 한글도 됨)
            if (key >= 32 && key <= 126) {
                buffer.append(key);
            }
        }
    }

    private void execute() {
        String cmdv = buffer.toString().trim(); // 앞뒤 공백 제거
        if (cmdv.isEmpty()) return;

        // 1. 로그 기록 (시간 + 입력값)
        String time = String.format("[%tT]", System.currentTimeMillis());
        logs.add(time + " root: " + cmdv);

        // 2. 단어 쪼개기
        String[] args = cmdv.split(" ");
        String cmd = args[0].toLowerCase(); // 첫 번째 단어를 소문자로!

        // 3. 명령어 판단 (여기서 cmdv 대신 cmd를 써야 대소문자 무시가 됨)
        if (cmd.equals("clear")) {
            logs.clear();
        }
        else if (cmd.equals("exit")) {
            System.exit(0);
        }
        else if (cmd.equals("close")) {
            isOpen = false;
            logs.add("[Console] " + "Console closed.");
        }
        else if (cmd.equals("dataload")) {
            main.load();
            logs.add("[Console] "+ "Profile" + main.profileId + " data loaded.");
        }
        else if (cmd.equals("pizza")) {
            if (main.pizza) {
                main.pizza = false;
            } else {
                main.pizza = true;
            }
            logs.add("[Console] " + "Is very good.");
        }

        else if (cmd.equals("set")) { // "exit"가 아니라 "set"일 때 로직
            if (args.length == 4) {
                String target = args[1];
                try {
                    if (target.equals("val")) {
                        if (args[2].equals("speed")) {
                            int value = Integer.parseInt(args[3]);
                            logs.add("[Console] " + target + " set to " + value);
                        } else if (args[2].equals("speed")) {
                            int value = Integer.parseInt(args[3]);
                            if (value >= 3 || value <= -1) value = 0;
                            logs.add("[Console] " + target + " set to " + value);
                        } else if (args[2].equals("setting1")) {
                            int value = Integer.parseInt(args[3]);
                            if (value >= 3 || value <= -1) value = 0;
                            main.getSettingManager().setSettingIndex(0,value);
                            logs.add("[Console] " + target + " set to " + value);
                        } else if (args[2].equals("setting2")) {
                            int value = Integer.parseInt(args[3]);
                            if (value >= 5 || value <= -1) value = 0;
                            main.getSettingManager().setSettingIndex(1,value);
                            logs.add("[Console] " + target + " set to " + value);
                        } else if (args[2].equals("setting3")) {
                            int value = Integer.parseInt(args[3]);
                            if (value >= 3 || value <= -1) value = 0;
                            main.getSettingManager().setSettingIndex(2,value);
                            logs.add("[Console] " + target + " set to " + value);
                        } else if (args[2].equals("setting4")) {
                            int value = Integer.parseInt(args[3]);
                            if (value >= 3 || value <= -1) value = 0;
                            main.getSettingManager().setSettingIndex(3,value);
                            logs.add("[Console] " + target + " set to " + value);
                        } else if (args[2].equals("setting5")) {
                            int value = Integer.parseInt(args[3]);
                            if (value >= 3 || value <= -1) value = 0;
                            main.getSettingManager().setSettingIndex(4,value);
                            logs.add("[Console] " + target + " set to " + value);
                        } else {
                            logs.add("[Console] Error : unknown val");
                        }
                    } else if (target.equals("bool")) {
                        if (args[2].equals("screenchangeeffect")) {
                            boolean value = Boolean.parseBoolean(args[3]);
                            main.getShutter().setScreenEffect(value);
                            logs.add("[Console] " + target + " set to " + value);
                        } else {
                            logs.add("[Console] Error : unknown bool");
                        }
                    }

                } catch (Exception e) {
                    logs.add("[Console] Error : wrong setter fun");
                }
            } else {
                logs.add("[Console] Usage: set [type] [name] [value]");
            }
        }
        else {
            logs.add("[System] Unknown command: " + cmd);
        }

        buffer.setLength(0);
    }

    public void render(Graphics g) {
        if (!isOpen) return;
        Graphics2D g2 = (Graphics2D) g;

        // 1. 배경 (반투명 블랙)
        g2.setColor(new Color(10, 10, 10, 240)); // 거의 불투명하게
        g2.fillRect(0, 0, 1920, 340); // 화면 절반 덮기

        // 2. 하단 구분선 (포인트 컬러: Scope Red)
        g2.setColor(new Color(255, 50, 30));
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(0, 340, 1920, 340);

        // -----------------------------------------------------
        // [Left Zone] 명령어 로그 창
        // -----------------------------------------------------
        g2.setFont(new Font("Consolas", Font.PLAIN, 16)); // Consolas나 Monospaced 권장
        int lineHeight = 25;
        int startY = 40;

        // 최근 N개의 로그만 보여주기 (스크롤 효과)
        int startIndex = Math.max(0, logs.size() - MAX_LINES);
        int lineCount = 0;

        for (int i = startIndex; i < logs.size(); i++) {
            String line = logs.get(i);

            // 컬러 코딩 로직 (간단하게)
            if (line.contains("Error")) g2.setColor(new Color(255, 80, 80)); // Red
            else if (line.contains("root:")) g2.setColor(Color.WHITE);       // Me
            else if (line.contains("[System]")) g2.setColor(Color.GREEN);    // System
            else g2.setColor(Color.LIGHT_GRAY);                              // Others

            g2.drawString(line, 30, startY + (lineCount * lineHeight));
            lineCount++;
        }

        // -----------------------------------------------------
        // [Input Line] 입력창 (맨 아래 고정)
        // -----------------------------------------------------
        g2.setColor(new Color(255, 50, 30));
        g2.setFont(new Font("Consolas", Font.BOLD, 18));
        String cursor = (System.currentTimeMillis() % 1000 > 300) ? "_" : "";
        g2.drawString("root@tss:~$ " + buffer.toString() + cursor, 30, 320);

        // -----------------------------------------------------
        // [Right Zone] 시스템 모니터 (간지용 대시보드)
        // -----------------------------------------------------
        // 구분선 (세로)

        g2.setColor(Color.DARK_GRAY);
        g2.drawLine(1500, 20, 1500, 320);

        g2.setFont(new Font("Impact", Font.PLAIN, 24));
        g2.setColor(Color.WHITE);
        g2.drawString("TSS GAME STATUS", 1520, 60);

        g2.setFont(new Font("Consolas", Font.BOLD, 14));
        g2.setColor(Color.GRAY);


        int y = 100;
        drawStat(g2, "FPS", "60 (Fixed)", Color.CYAN, 1520, y); y+=30;
        drawStat(g2, "MEMORY", scopeEngine.system().getUsedMemory() + "MB / " + scopeEngine.system().getTotalMemory() + "MB", Color.YELLOW, 1520, y); y+=30;
        drawStat(g2, "THREADS", Thread.activeCount() + " Active", Color.WHITE, 1520, y); y+=30;
        drawStat(g2, "CPU", scopeEngine.system().getCpuPercentage() +"%", Color.RED, 1520, y); y+=30;

        // 밑에 Scope 로고 작게
        g2.setColor(new Color(40, 40, 40));
        g2.setFont(new Font("Impact", Font.ITALIC, 40));
        g2.drawString("THE STINT : SAKHIR", 1600, 300);
    }

    // 모니터링용 헬퍼 함수
    private void drawStat(Graphics2D g, String label, String value, Color valColor, int x, int y) {
        g.setColor(Color.GRAY);
        g.drawString(label, x, y);
        g.setColor(valColor);
        g.drawString(value, x + 120, y);
    }
}