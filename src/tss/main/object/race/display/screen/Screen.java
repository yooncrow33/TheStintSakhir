package tss.main.object.race.display.screen;

import tss.main.object.race.display.Config;
import tss.main.object.race.display.Env;
import tss.main.object.race.display.IDisplay;

import java.awt.*;
import java.util.ArrayList;

abstract public class Screen {
    final int width = 1020;
    final int height = 630;
    public final String name;
    public abstract void render(Graphics g, int x, int y);
    Env env;

    public Screen(IDisplay iDisplay, String name) {
        this.env = new Env(iDisplay);
        this.name = name;
    }

    public void setFocusIndexFirst() {env.focusIndex = 0;}
    public void setFocusIndexLast() {env.focusIndex = env.getConfigs().size() - 1; }

    public Env getEnv() {return env;}

    public void up() {
        env.up();
    }
    public void down() {
        env.down();
    }
    public void left() {
        env.left();
    }
    public void right() {
        env.right();
    }
    public void action() {
        env.action();
    }
    public void update() {
        env.update();
    }
}
