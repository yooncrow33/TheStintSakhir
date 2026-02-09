package tss.main;

import tss.main.object.Race;

import java.awt.*;

import static java.awt.Color.blue;

public class GraphicsManager {
    final Main main;

    public GraphicsManager(Main main) {
        this.main = main;
    }

    // 밀리초: 0 ~ 999까지만 반환
    private int getMs(double tick) {
        return (int) (( tick * 16) % 1000);
    }

    // 초: 0 ~ 59까지만 반환
    private int getSe(double tick) {
        return (int) ((( tick * 16) / 1000) % 60);
    }

    // 분: 0 ~ 59까지만 반환
    private int getMi(double tick) {
        return (int) ((( tick * 16) / (1000 * 60)) % 60);
    }

    // 시: 제한 없이 쭉 올라감
    private int getHo(double tick) {
        return (int) (( tick * 16) / (1000 * 60 * 60));
    }

    public void renderMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // 1. 안티앨리어싱 활성화 (이거 꼭 해라, 폰트가 맥북처럼 매끄러워짐)

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
        g.drawString("VERSION: v12-dev-build", 55, 265);

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

        g.setColor(new Color(240,240,240));
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
            g.setColor(isFocused ? new Color(240,240,240) : Color.DARK_GRAY);
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

    public void renderEngineeringFullView(Graphics g, Race race) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- [ 1. 전체 구역 좌표 정의 (Core Layout) ] ---
        int screenW = 1920;
        int screenH = 1080;
        int radioH = 100; // 하단 라디오 높이
        int sideW = 450;  // 사이드바 너비 확대 (정보 가독성 업)
        int bottomH = 350; // 하단 텔레메트리 높이 확대

        Rectangle rectA = new Rectangle(0, 0, sideW, screenH - radioH); // 좌측: 지도 & 리더보드
        Rectangle rectC = new Rectangle(screenW - sideW, 0, sideW, screenH - radioH); // 우측: 전략 & 환경
        Rectangle rectB = new Rectangle(sideW, screenH - radioH - bottomH, screenW - (sideW * 2), bottomH); // 하단: 텔레메트리
        Rectangle rectMain = new Rectangle(sideW, 0, screenW - (sideW * 2), screenH - radioH - bottomH); // 중앙: 메인 콘솔
        Rectangle rectD = new Rectangle(0, screenH - radioH, screenW, radioH); // 최하단: 라디오

        // --- [ 2. 테마 설정 ] ---
        Color accentRed = new Color(255, 50, 30);
        Color lineGray = new Color(70, 70, 70);
        Color darkPanel = new Color(30,30,30);
        Color textGray = new Color(160, 160, 160);

        Font headFont = new Font("Impact", Font.PLAIN, 26);
        Font dataFont = new Font("Monospaced", Font.BOLD, 16);
        Font subFont = new Font("Impact", Font.PLAIN, 14);

        // -------------------------------------------------------------------------
        // [구역 A] 좌측 : GPS & 리더보드 (확대됨)
        // -------------------------------------------------------------------------
        g2d.setColor(darkPanel);
        g2d.fill(rectA);
        g2d.setColor(lineGray);
        g2d.draw(rectA);

        // ① 트랙 진행도 (직선)
        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("GPS TRACK PROGRESS", rectA.x + 25, 45);
        g2d.setColor(lineGray);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(rectA.x + 30, 90, rectA.x + sideW - 30, 90);
        g2d.fillOval(rectA.x + 30 + (int)(0.7 * 390), 82, 16, 16);

        // ② 리더보드 (20인 정밀 리스트)
        g2d.setFont(headFont);
        g2d.setColor(accentRed);
        g2d.drawString("LIVE LEADERBOARD", rectA.x + 25, 150);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 15));
        g2d.setColor(textGray);
        g2d.drawString("POS  DRIVER         TYRE   GAP/INT", rectA.x + 25, 185);

        for (int i = 0; i < 20; i++) {
            int rowY = 220 + (i * 38);
            g2d.setColor(new Color(30, 30, 30));
            g2d.drawLine(rectA.x + 20, rowY + 8, rectA.x + sideW - 20, rowY + 8);
            g2d.setColor(new Color(240,240,240));
            // [DATA: 순위, 드라이버, 타이어, 갭 실시간 데이터 연결]
            g2d.drawString(String.format("%02d   DRV_%-10s [%s]    +%.3f", i+1, "---", "-", 0.0), rectA.x + 25, rowY);
        }

        // -------------------------------------------------------------------------
        // [구역 B] 중앙 하단 : 초정밀 텔레메트리 (확대됨)
        // -------------------------------------------------------------------------
        g2d.setColor(new Color(30, 30, 30));
        g2d.fill(rectB);
        g2d.setColor(lineGray);
        g2d.draw(rectB);

        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("SYSTEM TELEMETRY : SCOPE_LINK", rectB.x + 20, rectB.y + 40);

        // ① 타이어 모니터 (간격 최적화)
        String[] tNames = {"FL", "FR", "RL", "RR"};
        for (int i = 0; i < 4; i++) {
            int tx = rectB.x + 30 + (i * 110);
            int ty = rectB.y + 70;
            g2d.setColor(lineGray);
            g2d.drawRect(tx, ty, 90, 180);
            g2d.setFont(subFont);
            g2d.setColor(accentRed);
            g2d.drawString(tNames[i], tx + 8, ty + 20);

            // [DATA: 마모도/온도/브레이크]
            g2d.setFont(dataFont);
            g2d.setColor(new Color(240,240,240));
            g2d.drawString("92°C", tx + 8, ty + 50);
            g2d.setColor(new Color(40, 40, 40));
            g2d.fillRect(tx + 55, ty + 30, 25, 130);
            g2d.setColor(new Color(0, 255, 100)); // 상태 좋음
            g2d.fillRect(tx + 55, ty + 30 + 30, 25, 100); // [DATA: 마모 바]
        }

        // ② 엔진 & 에어로 미니 모듈 (그리드화)
        int modX = rectB.x + 500;
        for (int i = 0; i < 8; i++) {
            int mx = modX + (i % 4 * 120);
            int my = rectB.y + 70 + (i / 4 * 65);
            g2d.setColor(lineGray);
            g2d.drawRect(mx, my, 110, 55);
            g2d.setFont(new Font("Impact", Font.PLAIN, 17));
            g2d.setColor(textGray);
            g2d.drawString("UNIT_" + i, mx + 8, my + 18);
            g2d.setFont( new Font("Monospaced", Font.BOLD, 16));
            g2d.setColor(new Color(240,240,240));
            g2d.drawString("---", mx + 8, my + 42); // [DATA: 엔진 수치]
        }

        g.setColor(new Color(50,50,50));
        g.fillRect(rectB.x + 500, rectB.y + 200, 475, 80);
        g.setFont(new Font("Impact", Font.BOLD,24));
        g.setColor(new Color(240,240,240));
        g.drawString("ERS", rectB.x + 510, rectB.y + 230);
        g.setColor(new Color(240,200,50));
        g.fillRect(rectB.x + 505,  rectB.y + 245, 465, 30);

        // -------------------------------------------------------------------------
        // [구역 C] 우측 : 전략 분석 & 환경 (확대됨)
        // -------------------------------------------------------------------------
        g2d.setColor(darkPanel);
        g2d.fill(rectC);
        g2d.setColor(lineGray);
        g2d.draw(rectC);

        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("STRATEGIC ANALYSIS", rectC.x + 25, 45);

        // ① 섹터 타임 & 델타
        g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
        g2d.setColor(new Color(240,240,240));
        //g2d.drawString("CUR: --:--.---", rectC.x + 30, 95);
        g2d.drawString(String.format("CUR: %02d:%02d.%03d", getMi(race.getPlayerCar().getCurrentLap().lapTimeTick),getSe(race.getPlayerCar().getCurrentLap().lapTimeTick),getMs(race.getPlayerCar().getCurrentLap().lapTimeTick)), rectC.x + 30, 95);
        for(int i=0; i<3; i++) {
            int sy = 140 + (i * 45);
            g2d.setFont(dataFont);
            g2d.setColor(textGray);
            g2d.drawString("SECTOR " + (i+1) + ":", rectC.x + 30, sy);
            g2d.setColor(Color.GREEN);
            switch (i) {
                case 0:
                    g2d.drawString(String.format("%02d.%03d", getSe(race.getPlayerCar().getCurrentLap().s1Tick), getMs(race.getPlayerCar().getCurrentLap().s1Tick)), rectC.x + 130, sy);
                    break;
                case 1:
                    g2d.drawString(String.format("%02d.%03d", getSe(race.getPlayerCar().getCurrentLap().s2TIck), getMs(race.getPlayerCar().getCurrentLap().s2TIck)), rectC.x + 130, sy);
                    break;
                case 2:
                    g2d.drawString(String.format("%02d.%03d", getSe(race.getPlayerCar().getCurrentLap().s3Tick), getMs(race.getPlayerCar().getCurrentLap().s3Tick)), rectC.x + 130, sy);
                    break;
                default:
                    g2d.drawString(String.format("%02d.%03d", 2, 2), rectC.x + 130, sy);
            }
            System.out.println(race.getPlayerCar().getCurrentLap().distance + "/" + race.getPlayerCar().getCurrentLap().tick);

            //g2d.drawString("--.--- (-0.---)", rectC.x + 130, sy); // [DATA: 섹터타임]
        }

        // ② 레이스 페이스 & 날씨 그래프
        int graphY = 320;
        g2d.setColor(accentRed);
        g2d.setFont(headFont);
        g2d.drawString("ENVIRONMENTAL FORECAST", rectC.x + 25, graphY);
        g2d.setColor(new Color(30, 30, 30));
        g2d.fillRect(rectC.x + 25, graphY + 30, sideW - 50, 200);
        g2d.setColor(lineGray);
        g2d.drawRect(rectC.x + 25, graphY + 30, sideW - 50, 200);
        // [DATA: 웨더 그래프 Polyline 구현]

        // ③ 환경 세부 데이터 모듈
        for(int i=0; i<4; i++) {
            int ex = rectC.x + 25 + (i % 2 * 205);
            int ey = graphY + 250 + (i / 2 * 65);
            g2d.setColor(lineGray);
            g2d.drawRect(ex, ey, 195, 55);
            g2d.setFont(subFont);
            g2d.drawString("ENV_SENSOR_" + i, ex + 10, ey + 20);
            g2d.setFont(dataFont);
            g2d.setColor(Color.CYAN);
            g2d.drawString("---", ex + 10, ey + 45); // [DATA: 온도/습도 등]
        }

        // -------------------------------------------------------------------------
        // [구역 Main] 중앙 메인 디스플레이 (조작 전용, 컴팩트화)
        // -------------------------------------------------------------------------
        g2d.setColor(new Color(30, 30, 30)); // 메인은 약간 붉은 기운이 도는 블랙
        g2d.fill(rectMain);
        g2d.setColor(accentRed);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(rectMain);

        g2d.setFont(new Font("Impact", Font.ITALIC, 48));
        g2d.drawString("COMMAND CONSOLE", rectMain.x + 40, rectMain.y + 70);
        g2d.setFont(subFont);
        g2d.setColor(textGray);
        g2d.drawString("INPUT SYSTEM READY... WAITING FOR USER ACTION", rectMain.x + 45, rectMain.y + 100);

        // [이곳에 나중에 조작용 큰 버튼들이나 GUI 요소가 들어갈 것임]

        // -------------------------------------------------------------------------
        // [구역 D] 최하단 : 라디오 로그
        // -------------------------------------------------------------------------
        g2d.setColor(new Color(30, 30, 30));
        g2d.fill(rectD);
        g2d.setColor(accentRed);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, rectD.y, screenW, rectD.y);

        g2d.setFont(new Font("Dialog", Font.BOLD, 22));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("(DRIVER)  : ---", 60, rectD.y + 40);
        g2d.setColor(new Color(240,240,240));
        g2d.drawString("(ENGINEER): ---", 60, rectD.y + 80);
    }

    public void renderModeSelection(Graphics g, int selectedOption) {
        // --- [ 중앙 메인 콘솔 영역 정의 ] ---
        int mx = 450;
        int my = 200;
        int mw = 1020;
        int mh = 630;

        Graphics2D g2d = (Graphics2D) g;

        // 배경 (콘솔 베이스)
        g2d.setColor(new Color(15, 15, 15));
        g2d.fillRect(mx, my, mw, mh);

        // 테두리 및 상단 가이드
        g2d.setColor(new Color(255, 50, 30));
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(mx, my, mw, mh);

        g2d.setFont(new Font("Impact", Font.ITALIC, 50));
        g2d.drawString("SELECT SESSION PROFILE", mx + 50, my + 80);

        // --- [ 두 개의 버튼 배치 ] ---
        int btnW = 420;
        int btnH = 400;
        int btnY = my + 160;
        int gap = 60;

        // 1. WEEKEND 버튼 위치: mx + (mw/2) - btnW - gap/2
        int weekendX = 450 + (1020 / 2) - 420 - 30; // 510
        // 2. TEST 버튼 위치: mx + (mw/2) + gap/2
        int testX = 450 + (1020 / 2) + 30; // 990

        // ① [WEEKEND] 렌더링
        boolean isWSelected = (selectedOption == 0);
        renderModeButton(g2d, weekendX, btnY, btnW, btnH, "GO TO WEEKEND", "FULL GP RACE WEEKEND", isWSelected);

        // ② [TEST] 렌더링
        boolean isTSelected = (selectedOption == 1);
        renderModeButton(g2d, testX, btnY, btnW, btnH, "GO TO TEST", "PRIVATE TELEMETRY RUN", isTSelected);

        // 하단 조작 가이드
        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawString("USE [LEFT/RIGHT] TO NAVIGATE // [ENTER] TO CONFIRM MISSION", mx + 50, my + mh - 30);
    }

    private void renderModeButton(Graphics g, int x, int y, int w, int h, String title, String sub, boolean isSelected) {
        Color red = new Color(255, 50, 30);

        Graphics2D g2d = (Graphics2D) g;
        // 버튼 박스
        if (isSelected) {
            g2d.setColor(new Color(255, 50, 30, 40));
            g2d.fillRect(x, y, w, h);
            g2d.setColor(red);
            g2d.setStroke(new BasicStroke(6));
        } else {
            g2d.setColor(new Color(20, 20, 20));
            g2d.fillRect(x, y, w, h);
            g2d.setColor(new Color(60, 60, 60));
            g2d.setStroke(new BasicStroke(2));
        }
        g2d.drawRect(x, y, w, h);

        // 내부 텍스트 디자인
        g2d.setFont(new Font("Impact", Font.PLAIN, 44));
        g2d.setColor(isSelected ? new Color(240,240,240) : new Color(100, 100, 100));
        g2d.drawString(title, x + 30, y + 80);

        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2d.setColor(isSelected ? red : new Color(70, 70, 70));
        g2d.drawString(">> " + sub, x + 35, y + 130);

        // 선택 시 하단에 'READY' 표시
        if (isSelected) {
            g2d.setFont(new Font("Impact", Font.PLAIN, 24));
            g2d.drawString("PROFILE READY", x + 35, y + h - 40);
            // 화살표 데코
            g2d.drawString(">>>>", x + w - 100, y + h - 40);
        }
    }

    public void renderBackground(Graphics g) {
        g.setColor(new Color(20,20,20));
        g.fillRect(-2147483647/2,-2147483647/2,2147483647,2147483647);

    }
}
