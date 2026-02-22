package tss.main.object.race.display;

import java.util.ArrayList;

public class Env {
    protected ArrayList<Config> configs = new ArrayList<>();
    protected ArrayList<Action> actions = new ArrayList<>();
    public int focusIndex = 0;
    IDisplay iDisplay;

    public Env(IDisplay iDisplay) {
        this.iDisplay = iDisplay;
    }
    public void update() {
        for (Action e : actions) {
            e.update();
        }
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
        final int maxSize = configs.size() + actions.size() - 1;
        if (focusIndex >= maxSize) {
            iDisplay.prevTap();
        } else {
            focusIndex++;
        }
    }
    public void left() {
        if(focusIndex >= configs.size()) {return;}
        configs.get(focusIndex).down();
    }
    public void right() {
        if(focusIndex >= configs.size()) {return;}
        configs.get(focusIndex).up();
    }
    public void action() {
        int actionIdx = focusIndex - configs.size();

        if (actionIdx >= 0 && actionIdx < actions.size()) {
            actions.get(actionIdx).action();
        }
    }

    public boolean isAction(String name) {
        for (Action a :actions) {
            if (a.getName().equals(name)) return a.consumeAction();
        }
        return false;
    }



    public ArrayList<Config> getConfigs() {
        return configs;
    }


    public ArrayList<Action> getActions() {
        return actions;
    }
}
