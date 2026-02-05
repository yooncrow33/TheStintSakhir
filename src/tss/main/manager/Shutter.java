package tss.main.manager;

import tss.main.Main;

import java.awt.*;

public class Shutter {

    double progress = 0.0;
    double target = 1.0;
    double progressDegree = 0.07; // 이 숫자가 작을수록 더 부드럽고 느리게 멈춤
    private boolean changingScreen = false;

    int width = 10000;
    int height = 1080;

    private Main.GameScreenState nextScreen;

    Main main;

    public Shutter(Main main) {
        this.main = main;
        nextScreen = null;
    }

    public boolean isChangingScreen() {
        return changingScreen;
    }

    public void update() {
        if (changingScreen) {
            if (target - progress < 0.001) { // 일정 수치 이하로 좁혀지면 강제 종료
                changingScreen = false;
                progress = 0.0;
            }
            progress += (target - progress) * progressDegree;
            if (progress >= 0.48 && progress <= 0.52 && nextScreen != null) {
                main.setGameScreenState(nextScreen);
                nextScreen = null;
            }
        }
    }

    public void changScreen(Main.GameScreenState gss) {
        if (changingScreen) {
            if (progress < 0.80) return;
        }
        nextScreen = gss;
        progress = 0.0;
        changingScreen = true;
    }

    public void render(Graphics g) {
        if (!changingScreen) return;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        width = 10000;
        height = 1080;

        // progress가 0.0 ~ 0.5일 때 화면을 덮고, 0.5 ~ 1.0일 때 열림
        double coverProgress;
        if (progress < 0.5) {
            coverProgress = progress * 2; // 0.0 -> 1.0
        } else {
            coverProgress = 1.0 - ((progress - 0.5) * 2); // 1.0 -> 0.0
        }

        // 디자인: 듀얼 블레이드 (상하에서 닫히는 셔터)
        int shutterHeight = (int) (height / 2 * coverProgress);

        g2d.setColor(new Color(15, 15, 15)); // 배경색과 동일하게
        g2d.fillRect(-200, 0, width, shutterHeight); // 상단 셔터
        g2d.fillRect(-200, height - shutterHeight, width, shutterHeight); // 하단 셔터

        // 지리는 포인트: 셔터 경계면에 레드 라인 추가
        g2d.setColor(new Color(255, 50, 30));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(-200, shutterHeight, width, shutterHeight);
        g2d.drawLine(-200, height - shutterHeight, width, height - shutterHeight);
    }
}
