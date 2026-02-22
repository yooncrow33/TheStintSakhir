package tss.main.object.race.display;

import tss.main.object.Console;
import tss.main.object.race.display.screen.Screen;

import java.util.ArrayList;

public class Config {
    public final String name;
    public int value;
    private final int maxValue;
    private final int minValue;

    public Config(String Name, int max, int min) {
        this.name =Name;
        maxValue =max;
        minValue =min;
    }
    public void up() {
        if (value >= maxValue) {
            value = minValue;
        } else {
            value ++;
        }
    }
    public void down() {
        if (value <= minValue) {
            value = maxValue;
        } else {
            value --;
        }
    }
}
