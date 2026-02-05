package tss.main;

import java.awt.*;

public class GraphicsManager {
    final Main main;

    public GraphicsManager(Main main) {
        this.main = main;
    }

    public void renderMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // 1. 안티앨리어싱 활성화 (이거 꼭 해라, 폰트가 맥북처럼 매끄러워짐)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 2. 메인 타이틀 (The Grid : Sakhir)
        g.setColor(new Color(255, 50, 30));
        g.setFont(new Font("Impact", Font.BOLD, 108));
        g.drawString("THE STINT", 50, 120);

        // 서브 타이틀 (Sakhir) - 약간 작게 해서 세련미 추가
        g.setFont(new Font("Impact", Font.PLAIN, 60));
        g.drawString(" : SAKHIR", 460, 120);

        // 3. 드라이버 태그 라인 (가로선 하나 그어주면 개지림)
        g.setColor(Color.WHITE);
        g.fillRect(50, 150, 800, 2); // 얇은 구분선

        g.setFont(new Font("Impact", Font.PLAIN, 42));
        // 드라이버 이름과 번호를 더 강조
        String driverTag = main.firstName.toUpperCase() + " " + main.lastName.toUpperCase() + " | #" + main.driverNumber;
        g.drawString(driverTag, 55, 210);

        // 4. 하단 버전 정보 (엔지니어링 툴 느낌)
        g.setColor(Color.GRAY);
        g.setFont(new Font("Monospaced", Font.BOLD, 16)); // 고정폭 폰트로 터미널 느낌
        g.drawString("SYSTEM STATUS: OPTIMAL", 55, 245);
        g.drawString("VERSION: v01-dev-build", 55, 265);

        // 2. SETTING 버튼 (얇은 테두리 스타일)
        g.drawRect(1650, 860, 250, 60);
        g.setFont(new Font("Impact", Font.PLAIN, 32));
        g.setColor(Color.gray);
        g.drawString("SETTINGS", 1710, 900); // + 60 / + 40

        g.drawRect(1650, 940, 250, 60);
        g.drawString("EXIT", 1746, 980); // + 60 / + 40

        switch (main.menuFocus) {
            case 0 :
                g.setColor(new Color(255, 50, 30, 40)); // 아주 연한 레드 배경
                g.fillRect(1640, 750, 270, 100); // 버튼 배경
                g.setColor(new Color(255, 50, 30));
                g.drawRect(1640, 750, 270, 100); // 버튼 배경
                break;
            case 1 :
                g.setColor(new Color(255, 50, 30, 120)); // 포커스된 항목은 레드
                g.fillRect(1652, 862, 246, 56);
                g.setColor(new Color(240,240,240));
                g.drawString("SETTINGS", 1710, 900); // + 60 / + 40
                break;
            case 2 :
                g.setColor(new Color(255, 50, 30, 120)); // 포커스된 항목은 레드
                g.fillRect(1652, 942, 246, 56);
                g.setColor(new Color(240,240,240));
                g.drawString("EXIT", 1746, 980); // + 60 / + 40
                break;
            default:

        }

         // 5. 버튼 영역 (우측 하단 집중 배치)
        // 1. GO! 버튼 (크고 강렬하게)
        g.setColor(new Color(255, 50, 30));
        g.fillRect(1650, 760, 250, 80); // 버튼 배경
        g.setColor(new Color(240,240,240));
        g.setFont(new Font("Impact", Font.ITALIC, 54));
        g.drawString("GO!", 1730, 820); // + 80 / + 60



        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Dialog", Font.ITALIC, 16));
        g.drawString("ARROW TO MOVE  |  ENTER TO SELECT", 50, 1080 - 50);
    }

    public void renderSettings(Graphics g, Main main) {
        Graphics2D gd = (Graphics2D) g;
        // 디자인 가이드라인 상수
        final int START_X = 80;          // 좌측 여백
        final int START_Y = 180;         // 첫 항목 시작 높이
        final int ROW_HEIGHT = 65;       // 항목 간 간격
        final int LABEL_WIDTH = 350;     // 설정 이름 영역 가로폭
        final int SELECTOR_WIDTH = 400;  // 설정값 선택 영역 가로폭

        g.setColor(new Color(255, 50, 30));
        g.setFont(new Font("Impact", Font.BOLD, 80));
        g.drawString("SETTINGS", 50, 100);

        g.setColor(Color.WHITE);
        g.fillRect(50, 120, 400, 3); // 타이틀 밑 강조선

        // 2. 세팅 리스트 반복문 (예시 데이터라고 생각하고 봐)
        String[] settingLabels = {main.getSettingManager().getSettingName(0), main.getSettingManager().getSettingName(1),
                main.getSettingManager().getSettingName(2), main.getSettingManager().getSettingName(3), main.getSettingManager().getSettingName(4)};
        String[] settingValues = {main.getSettingManager().getCurrentSettingValue(0), main.getSettingManager().getCurrentSettingValue(1),
                main.getSettingManager().getCurrentSettingValue(2), main.getSettingManager().getCurrentSettingValue(3), main.getSettingManager().getCurrentSettingValue(4)};

        for (int i = 0; i < settingLabels.length; i++) {
            int currentY = START_Y + (i * ROW_HEIGHT);
            boolean isFocused = (i == main.getSettingManager().getFocus()); // 현재 선택된 항목인지 확인

            // 포커스된 항목 하이라이트 배경
            if (isFocused) {
                g.setColor(new Color(255, 50, 30, 40)); // 아주 연한 레드 배경
                g.fillRect(START_X - 20, currentY - 40, LABEL_WIDTH + SELECTOR_WIDTH + 40, ROW_HEIGHT - 5);
                g.setColor(new Color(255, 50, 30)); // 포커스된 항목은 레드
            } else {
                g.setColor(Color.GRAY); // 비활성 항목은 그레이
            }

            // A. 설정 이름 (Label)
            g.setFont(new Font("Impact", Font.PLAIN, 28));
            g.drawString(settingLabels[i], START_X, currentY);

            // B. 좌우 화살표 및 값 (Selector)
            int selectorX = START_X + LABEL_WIDTH;

            // 왼쪽 화살표 [<]
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString("<", selectorX, currentY);

            // 현재 값 (중앙 정렬 느낌을 위해 Monospaced 권장)
            g.setColor(isFocused ? Color.WHITE : Color.DARK_GRAY);
            g.setFont(new Font("Monospaced", Font.BOLD, 24));
            // 값의 길이에 상관없이 일정 위치에 출력
            g.drawString(settingValues[i], selectorX + 50, currentY - 2);

            // 오른쪽 화살표 [>]
            g.setColor(isFocused ? new Color(255, 50, 30) : Color.GRAY);
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString(">", selectorX + SELECTOR_WIDTH - 30, currentY);
        }

        if (main.getSettingManager().getFocus() == 5) {
            g.setColor(new Color(255, 50, 30,120)); // 포커스된 항목은 레드
            g.fillRect(1645, 945, 260, 70);
        }

        // 하단 도움말 (HHKB 유저를 위한 배려)
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Dialog", Font.ITALIC, 16));
        g.drawString("USE ARROW KEYS TO NAVIGATE  |  ENTER TO APPLY", 50, 1080 - 50);
        // 3. 뒤로 가기 버튼 (우측 하단)
        g.setColor(new Color(240,240,240));
        g.drawRect(1650, 950, 250, 60);
        g.setFont(new Font("Impact", Font.PLAIN, 32));
        g.drawString("BACK", 1740, 990); // + 60 / + 40
    }

    public void renderEngineeringFullView(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- [ 기초 환경 설정 ] ---
        int w = 1920;
        int h = 1080;
        Color accentRed = new Color(255, 50, 30);
        Color lineGray = new Color(60, 60, 60);
        Color darkPanel = new Color(15, 15, 15);
        Color textGray = new Color(150, 150, 150);

        Font headFont = new Font("Impact", Font.PLAIN, 22);
        Font dataFont = new Font("Monospaced", Font.BOLD, 14);
        Font cmdFont = new Font("Impact", Font.PLAIN, 36);

        // -------------------------------------------------------------------------
        // [구역 A] 좌측 사이드바 : 정보의 기둥 (350px 폭)
        // -------------------------------------------------------------------------
        // 배경 및 테두리
        g2d.setColor(darkPanel);
        g2d.fillRect(0, 0, 350, h - 100);
        g2d.setColor(lineGray);
        g2d.drawRect(0, 0, 350, h - 100);

        // ① 리니어 트랙 맵 (구역 A 상단)
        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("GPS TRACK PROGRESS", 20, 40);
        g2d.setColor(lineGray);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(30, 80, 320, 80); // 일직선 트랙
        // [DATA: 내 차 위치] g2d.fillOval(30 + (progress * 290), 75, 10, 10);

        // ② 20인 리더보드 (구역 A 나머지 전체)
        g2d.setColor(accentRed);
        g2d.drawString("LEADERBOARD (FULL 20)", 20, 130);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
        g2d.setColor(textGray);
        g2d.drawString("POS  DRV  TYRE   GAP/INT", 20, 155);

        for (int i = 0; i < 20; i++) {
            int y = 180 + (i * 38);
            g2d.setColor(lineGray);
            g2d.drawLine(20, y + 5, 330, y + 5);
            g2d.setColor(Color.WHITE);
            // [DATA: 순위, 드라이버, 타이어, 갭]
            g2d.drawString(String.format("%02d   ---  [-]    ---", i+1), 20, y);
        }

        // -------------------------------------------------------------------------
// [구역 B] 중앙 하단 : 초정밀 텔레메트리 (Mini-Monitor Grid)
// -------------------------------------------------------------------------
        int bx = 350;
        int bw = w - 700;
        int by = h - 320; // 메인 모니터 크기를 줄이기 위해 높이 조정
        int bh = 220;    // 텔레메트리 영역 자체 높이

// 전체 배경 (살짝 더 어둡게)
        g2d.setColor(new Color(5, 5, 5));
        g2d.fillRect(bx, by, bw, bh);

// 1. TIRE CLUSTER (좌측 밀집 구역)
// 타이어 4개를 아주 좁게 붙여서 하나의 모듈처럼 보이게 함
        int tx_start = bx + 10;
        String[] tNames = {"FL", "FR", "RL", "RR"};
        for (int i = 0; i < 4; i++) {
            int tx = tx_start + (i * 75); // 간격을 75px로 대폭 축소
            int ty = by + 20;

            // 개별 타이어 미니 모니터 프레임
            g2d.setColor(lineGray);
            g2d.drawRect(tx, ty, 70, 140);

            g2d.setFont(new Font("Impact", Font.PLAIN, 12));
            g2d.setColor(accentRed);
            g2d.drawString(tNames[i], tx + 5, ty + 15);

            // [DATA: 타이어 온도 숫자]
            g2d.setFont(dataFont);
            g2d.setColor(Color.WHITE);
            g2d.drawString("90°", tx + 5, ty + 35);

            // [DATA: 타이어 마모도 수직 바]
            g2d.setColor(new Color(40, 40, 40));
            g2d.fillRect(tx + 40, ty + 25, 20, 100); // 배경
            g2d.setColor(new Color(0, 255, 100));
            g2d.fillRect(tx + 40, ty + 25 + 20, 20, 80); // [DATA: 실제 마모량]

            // [DATA: 브레이크 온도]
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
            g2d.setColor(textGray);
            g2d.drawString("BRK:450°", tx + 5, ty + 135);
        }

// 2. THERMAL & ENGINE MODULES (중앙부 쪼개기)
        int mx = tx_start + 310;
        String[][] engineData = {
                {"WATER", "95°C"}, {"OIL T", "110°C"},
                {"OIL P", "5.2b"}, {"GEAR", "82°C"}
        };

        for (int i = 0; i < 4; i++) {
            int ex = mx + (i * 85);
            g2d.setColor(lineGray);
            g2d.drawRect(ex, by + 20, 80, 45); // 개별 데이터 칸

            g2d.setFont(new Font("Impact", Font.PLAIN, 10));
            g2d.setColor(textGray);
            g2d.drawString(engineData[i][0], ex + 5, by + 35);

            g2d.setFont(dataFont);
            g2d.setColor(Color.WHITE);
            g2d.drawString(engineData[i][1], ex + 5, by + 58);
        }

// 3. AERO & DAMAGE GRID (중앙 하단 쪼개기)
        String[][] aeroData = {
                {"W-FRNT", "100%"}, {"W-REAR", "100%"},
                {"FLOOR", " 98%"}, {"DIFFU", "100%"}
        };

        for (int i = 0; i < 4; i++) {
            int ax = mx + (i * 85);
            g2d.setColor(lineGray);
            g2d.drawRect(ax, by + 75, 80, 45);

            g2d.setFont(new Font("Impact", Font.PLAIN, 10));
            g2d.setColor(textGray);
            g2d.drawString(aeroData[i][0], ax + 5, by + 90);

            g2d.setFont(dataFont);
            g2d.setColor(new Color(0, 255, 0));
            g2d.drawString(aeroData[i][1], ax + 5, by + 113);
        }

// 4. RESOURCE & ERS MODULES (우측 밀집 구역)
        int rx = mx + 350;
// ERS 메인 모니터
        g2d.setColor(lineGray);
        g2d.drawRect(rx, by + 20, 220, 100);
        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("ENERGY UNIT", rx + 10, by + 45);

// [DATA: ERS Bar]
        g2d.setColor(new Color(40, 40, 40));
        g2d.fillRect(rx + 10, by + 60, 200, 15);
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(rx + 10, by + 60, 160, 15); // 80% 잔량 예시

        g2d.setFont(dataFont);
        g2d.drawString("STORED: 3.82MJ", rx + 10, by + 95);

// FUEL 미니 모니터
        g2d.setColor(lineGray);
        g2d.drawRect(rx, by + 130, 220, 50);
        g2d.setColor(new Color(0, 200, 255));
        g2d.setFont(new Font("Impact", Font.PLAIN, 14));
        g2d.drawString("FUEL REMAINING", rx + 10, by + 145);
        g2d.setFont(dataFont);
        g2d.drawString("42.58 L  |  +0.12", rx + 10, by + 170); // [DATA: 남은 연료 및 델타]

// 5. SYSTEM STATUS 바 (구역 B 최하단 한 줄)
        g2d.setColor(new Color(25, 25, 25));
        g2d.fillRect(bx, by + 190, bw, 30);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2d.setColor(Color.GREEN);
        g2d.drawString("TELEMETRY LINK: ACTIVE // SAMPLE RATE: 1000Hz // ENCRYPTION: SCOPE_SECURE", bx + 15, by + 210);

        // -------------------------------------------------------------------------
        // [구역 C] 우측 메인 : 커맨드 센터 (정보 밀집 구역)
        // -------------------------------------------------------------------------
        g2d.setColor(darkPanel);
        g2d.fillRect(rx, 0, 350, h - 100);
        g2d.setColor(lineGray);
        g2d.drawRect(rx, 0, 350, h - 100);

        // ① Top: Sector & Lap Times
        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("TIME ANALYSIS", rx + 20, 40);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
        g2d.setColor(Color.WHITE);
        g2d.drawString("LAP: --:--.---", rx + 20, 80); // [DATA: 현재 랩타임]
        g2d.setFont(dataFont);
        g2d.drawString("S1: --.---", rx + 20, 110); // [DATA: 섹터 1]
        g2d.drawString("S2: --.---", rx + 20, 130); // [DATA: 섹터 2]
        g2d.drawString("S3: --.---", rx + 20, 150); // [DATA: 섹터 3]

        // ② Middle: Main Command Monitor (여기가 메인 놀이터)
        int mw = 310;
        int mh = 400;
        g2d.setColor(accentRed);
        g2d.drawRect(rx + 20, 200, mw, mh);
        g2d.setFont(headFont);
        g2d.drawString("ACTIVE COMMANDS", rx + 30, 230);

        // [COMMAND LIST] - 키보드로 조작할 곳
        String[] commandLabels = {"[BOX BOX]", "[PUSH MODE]", "[CONSERVE]", "[EASY MODE]"};
        for(int i=0; i<4; i++) {
            g2d.setFont(cmdFont);
            g2d.setColor(i == 0 ? accentRed : lineGray); // [DATA: menuFocus 변수 연결]
            g2d.drawString(commandLabels[i], rx + 45, 300 + (i * 80));
        }

        // ③ Bottom: Strategy & Weather
        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("WEATHER / FORECAST", rx + 20, 650);
        g2d.setColor(lineGray);
        g2d.drawRect(rx + 20, 670, 310, 150); // [DATA: 웨더 레이더 그래프]
        g2d.setFont(dataFont);
        g2d.drawString("RAIN PROB: --%", rx + 30, 840);

        // -------------------------------------------------------------------------
        // [구역 D] 최하단 : 라디오 로그 (커뮤니케이션 바)
        // -------------------------------------------------------------------------
        g2d.setColor(new Color(10, 10, 10));
        g2d.fillRect(0, h - 100, w, 100);
        g2d.setColor(accentRed);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(0, h - 100, w, h - 100); // 구분선

        g2d.setFont(new Font("Dialog", Font.BOLD, 20));
        // [DATA: 라디오 텍스트 데이터]
        g2d.setColor(Color.YELLOW);
        g2d.drawString("(DRIVER) : ---", 50, h - 60);
        g2d.setColor(Color.WHITE);
        g2d.drawString("(ENGINEER): ---", 50, h - 25);
    }

    public void renderBackground(Graphics g) {
        g.setColor(new Color(20,20,20));
        g.fillRect(-2147483647/2,-2147483647/2,2147483647,2147483647);

    }
}
