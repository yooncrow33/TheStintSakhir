package tss.main.object.race.display;

import java.util.ArrayList;

public class Env {
    protected ArrayList<Config> configs = new ArrayList<>();
    public int focusIndex = 0;
    IDisplay iDisplay;

    public Env(IDisplay iDisplay) {
        this.iDisplay = iDisplay;
    }
    public void up() {
        if (focusIndex == 0) {
            iDisplay.nextTap();
        }
        else {
            focusIndex--;
        }
    }
    public void down() {
        if (focusIndex >= configs.size() - 1) {
            iDisplay.prevTap();
        } else {
            focusIndex++;
        }
    }
    public void left() {
        configs.get(focusIndex).down();
    }
    public void right() {
        configs.get(focusIndex).up();
    }

    public ArrayList<Config> getConfigs() {
        return configs;
    }
}
