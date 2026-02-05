package tss.launch;

import scope.Base;
import java.awt.*;

public class Store extends Base {

    public Store(String title) {
        super(title);
    }

    @Override protected void init() {
    }

    @Override protected void update(double dt) {
    }

    @Override protected void render(Graphics g) {
        g.setColor(new Color(30,30,30));
        g.fillRect(-20000,-20000,60000,60000);
        g.setColor(new Color(80,80,80));
        g.fillRect(0,0,200,1080);
        g.setColor(new Color(240,240,240));

        String gameName[] = {"Ice Drop", "Ice & Run","The Stint : Sakhir"};
        String gameEx[] = {"A simple Tycoon Game", "A Side Scrolling Prototype","A Race Engineering Game"};
        String gamePrice[] = {"5,300 points","Free","7,980 points"};
        for (int i = 0; i < gameName.length; i++) {
            g.setFont(new Font("Impact", Font.BOLD, 36));
            g.drawString(gameName[i], 250, 200 + i * 150);
            g.setFont(new Font("Dialog", Font.PLAIN, 20));
            g.drawString(gameEx[i], 250, 240 + i * 150);
            g.setFont(new Font("Dialog", Font.BOLD, 24));
            g.drawString(gamePrice[i], 250, 280 + i * 150);
            g.setColor(new Color(240,240,240));
            g.drawRect(210, 160 + i * 150, 400, 140);
        }

        g.setFont(new Font("Impact", Font.BOLD, 92));
        g.setColor(new Color(255,50,30));
        g.drawString("Scorave Store", 210, 100);
    }

    public static void main(String[] args) {
        new Store("Scorave Store");
    }

}
