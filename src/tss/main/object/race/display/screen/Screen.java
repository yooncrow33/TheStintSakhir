package tss.main.object.race.display.screen;

import tss.main.object.race.display.Config;
import tss.main.object.race.display.Env;
import tss.main.object.race.display.IDisplay;

import java.awt.*;
import java.util.ArrayList;

abstract public class Screen {
    final int width = 1020;
    final int height = 630;
    public abstract void render(Graphics g, int x, int y);
    Env env;

    public Screen(IDisplay iDisplay) {
        this.env = new Env(iDisplay);
    }

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
}
