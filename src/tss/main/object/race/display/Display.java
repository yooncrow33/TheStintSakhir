package tss.main.object.race.display;

import tss.main.object.Race;
import tss.main.object.race.display.screen.Laps;
import tss.main.object.race.display.screen.Screen;
import tss.main.object.race.display.screen.Setup;

import java.awt.*;
import java.util.ArrayList;

public class Display implements IDisplay {
    final int x = 450;
    final int y = 0;
    final int width = 1020;
    final int height = 630;

    enum TabState {
        laps(),
        tire(),
        pit(),
        setup(),
        pace();
    }
    TabState tabState = TabState.laps;

    Race race;

    ArrayList<Screen> screens = new ArrayList<>();
    Laps laps = new Laps(this);
    Setup setup = new Setup(this);

    public Display(Race race) {
        this.race=  race;
        screens.add(laps);
        screens.add(setup);
    }
    public void render(Graphics g) {
        g.setFont(new Font("Impact", Font.BOLD, 42));
        g.drawString(screens.get(tabState.ordinal()).name,x + 10, y + 42);
        screens.get(tabState.ordinal()).render(g,x,y + 50);
    }

    @Override
    public void nextTap() {
        switch (tabState) {
            case laps -> tabState = TabState.tire;
            case tire -> tabState = TabState.pit;
            case pit -> tabState = TabState.setup;
            case  setup-> tabState = TabState.pace;
            case pace -> tabState = TabState.laps;
        }
        screens.get(tabState.ordinal()).setFocusIndexLast();
    }
    @Override
    public void prevTap() {
        switch (tabState) {
            case laps -> tabState = TabState.pace;
            case tire -> tabState = TabState.laps;
            case pit -> tabState = TabState.tire;
            case setup -> tabState = TabState.pit;
            case pace -> tabState = TabState.setup;
        }
        screens.get(tabState.ordinal()).setFocusIndexFirst();
    }

    public void up() {
        screens.get(tabState.ordinal()).up();
    }
    public void down() {
        screens.get(tabState.ordinal()).down();
    }
    public void left() {
        screens.get(tabState.ordinal()).left();
    }
    public void right() {
        screens.get(tabState.ordinal()).right();
    }
    public void action() {
        screens.get(tabState.ordinal()).action();
    }
    public void update() {
        screens.get(tabState.ordinal()).update();
    }
}
