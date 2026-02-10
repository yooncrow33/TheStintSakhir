package tss.main.object.race.display;

import tss.main.object.Race;
import tss.main.object.race.display.screen.Laps;
import tss.main.object.race.display.screen.Screen;

import java.awt.*;
import java.util.ArrayList;

public class Display implements IDisplay {
    final int x = 450;
    final int y = 0;
    final int width = 1020;
    final int height = 630;

    enum TapState {
        laps(),
        tire(),
        pit(),
        setup(),
        pace();
    }
    TapState tapState = TapState.laps;

    Race race;

    ArrayList<Screen> screens = new ArrayList<>();
    Laps laps = new Laps(this);

    public Display(Race race) {
        this.race=  race;
        screens.add(laps);
    }
    public void render(Graphics g) {
        screens.get(tapState.ordinal()).render(g,x,y);
    }

    @Override
    public void nextTap() {
        switch (tapState) {
            case laps -> tapState = TapState.tire;
            case tire -> tapState = TapState.pit;
            case pit -> tapState = TapState.setup;
            case  setup-> tapState = TapState.pace;
            case pace -> tapState = TapState.laps;
        }
    }
    @Override
    public void prevTap() {
        switch (tapState) {
            case laps -> tapState = TapState.pace;
            case tire -> tapState = TapState.laps;
            case pit -> tapState = TapState.tire;
            case setup -> tapState = TapState.pit;
            case pace -> tapState = TapState.setup;
        }
    }

    public void up() {
        screens.get(tapState.ordinal()).up();
    }
    public void down() {
        screens.get(tapState.ordinal()).down();
    }
    public void left() {
        screens.get(tapState.ordinal()).left();
    }
    public void right() {
        screens.get(tapState.ordinal()).right();
    }
}
