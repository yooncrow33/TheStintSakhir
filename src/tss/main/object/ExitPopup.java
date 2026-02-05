package tss.main.object;

import java.awt.*;

public class ExitPopup {
    boolean visible = false;
    boolean isYesSelected = false;
    public void setVisible() {
        visible = true;
    }
    public boolean isVisible() {
        return visible;
    }
    public void select() {
        if (isYesSelected) {
            System.exit(0);
        } else {
            visible = false;
        }
    }
    public void move() {
        if (isYesSelected) {
            isYesSelected = false;
        } else {
            isYesSelected = true;
        }
    }
    public void render(Graphics g, String message, String title) {
        if (!visible) return;
        // 1. 백그라운드 딤(Dimming) 효과
        // 현재 화면 전체를 반투명한 검정으로 덮어버림
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(-2000, -2000, 60000, 6000);

        // 2. 팝업 박스 설정
        int pWidth = 500;
        int pHeight = 250;
        int px = (1920 - pWidth) / 2;
        int py = (1080 - pHeight) / 2;

        // 박스 배경 및 테두리
        g.setColor(new Color(20, 20, 20));
        g.fillRect(px, py, pWidth, pHeight);

        // 두꺼운 레드 상단 바 (경고 느낌)
        g.setColor(new Color(255, 50, 30));
        g.fillRect(px, py, pWidth, 5);
        g.drawRect(px, py, pWidth, pHeight); // 전체 테두리

        // 3. 텍스트 렌더링
        g.setColor(new Color(255, 50, 30));
        g.setFont(new Font("Impact", Font.BOLD, 32));
        g.drawString(title, px + 30, py + 50);

        g.setColor(new Color(240,240,240));
        g.setFont(new Font("Dialog", Font.BOLD, 18));
        // 사용자가 입력한 메시지 (예: "Do you want to exit to desktop?")
        g.drawString(message, px + 35, py + 100);

        // 4. 버튼 영역 (Yes / No)
        int btnWidth = 180;
        int btnHeight = 50;
        int btnY = py + 160;

        // [YES / ABORT] 버튼
        if (isYesSelected) {
            g.setColor(new Color(255, 50, 30));
            g.fillRect(px + 40, btnY, btnWidth, btnHeight);
            g.setColor(new Color(240,240,240));
        } else {
            g.setColor(Color.DARK_GRAY);
            g.drawRect(px + 40, btnY, btnWidth, btnHeight);
        }
        g.setFont(new Font("Impact", Font.PLAIN, 24));
        g.drawString("ABORT", px + 97, btnY + 33);

        // [NO / CONTINUE] 버튼
        if (!isYesSelected) {
            g.setColor(new Color(240,240,240)); // 선택 시 화이트 테두리 혹은 배경
            g.drawRect(px + 280, btnY, btnWidth, btnHeight);
            g.fillRect(px + 280, btnY, btnWidth, btnHeight);
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.drawRect(px + 280, btnY, btnWidth, btnHeight);
        }
        g.setFont(new Font("Impact", Font.PLAIN, 24));
        g.drawString("CONTINUE", px + 322, btnY + 33);
    }
}
