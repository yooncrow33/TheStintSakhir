package tss.main.object.race.display.screen;

import tss.main.object.race.display.Action;
import tss.main.object.race.display.Config;
import tss.main.object.race.display.IDisplay;

import java.awt.*;
import java.util.ArrayList;

public class Laps extends Screen {
    public Laps(IDisplay iDisplay) {
        super(iDisplay, "Laps");
        super.env.getConfigs().add(new Config("NVidia Fuck You", 20,0));
        super.env.getConfigs().add(new Config("setting 2", 20, -20));
        super.env.getActions().add(new Action("Driver out for drive."));
    }
    @Override
    public void render(Graphics g, int x, int y) {
        int sy2 = 0;
        for (int i = 0; super.env.getConfigs().size() > i; i++) {
            int sx = x + 10; int sy = y + 10; int slotHeight = 30; int rowHeight = 60; int selectorWidth = 400;
            boolean isFocus = super.env.focusIndex == i;
            int currentY = sy + 40 + (i * rowHeight);
            if (isFocus) {
                g.setColor(new Color(255, 50, 30, 40)); // 아주 연한 레드 배경
                g.fillRect(sx, currentY - 40,  width - 20, rowHeight);
                g.setColor(new Color(255, 50, 30)); // 포커스된 항목은 레드
            } else {
                g.setColor(Color.gray);
            }

            g.setFont(new Font("Impact", Font.PLAIN, 28));
            g.drawString(super.env.getConfigs().get(i).name, sx + 20, currentY);

            // 왼쪽 화살표 [<]
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString("<", sx + 550, currentY);

            // 현재 값 (중앙 정렬 느낌을 위해 Monospaced 권장)
            g.setColor(isFocus ? new Color(240,240,240) : Color.DARK_GRAY);
            g.setFont(new Font("Monospaced", Font.BOLD, 24));
            // 값의 길이에 상관없이 일정 위치에 출력
            g.drawString(Integer.toString(super.env.getConfigs().get(i).value), sx + 550 + 50, currentY - 2);

            // 오른쪽 화살표 [>]
            g.setColor(isFocus ? new Color(255, 50, 30) : Color.GRAY);
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString(">", sx + 550 + selectorWidth - 30, currentY);

            sy2 = currentY + 20;
        }
        for (int i = 0; super.env.getActions().size() > i; i++) {
            int sx = x + 10; int sy = sy2; int slotHeight = 30; int rowHeight = 60; int selectorWidth = 400;
            int configOffset = env.getConfigs().size();
            boolean isFocus = (env.focusIndex == (configOffset + i));
            int currentY = sy + 40 + (i * rowHeight);
            if (isFocus) {
                if (env.getActions().get(i).isHighLight()) {
                    g.setColor(new Color(255, 50, 30, 140)); // 아주 연한 레드 배경
                    g.fillRect(sx, currentY - 40,  width - 20, rowHeight);
                } else {
                    g.setColor(new Color(255, 50, 30, 40)); // 아주 연한 레드 배경
                    g.fillRect(sx, currentY - 40,  width - 20, rowHeight);
                }
                g.setColor(new Color(255, 50, 30)); // 포커스된 항목은 레드
            } else {
                g.setColor(Color.gray);
            }

            g.setFont(new Font("Impact", Font.PLAIN, 28));
            g.drawString(super.env.getActions().get(i).name, sx + 20, currentY);

            // 오른쪽 화살표 [>]
            g.setColor(isFocus ? new Color(255, 50, 30) : Color.GRAY);
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString("[ENTER]", sx + 420 + selectorWidth - 30, currentY);
        }
    }
}
