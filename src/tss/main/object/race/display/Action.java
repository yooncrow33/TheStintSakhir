package tss.main.object.race.display;

public class Action {
    public final String name;
    boolean b = false;
    boolean isHighLight = false;
    int highLightTime = 0;
    final int highLightCool = 7;
    public Action(String name) {
        this.name = name;
    }

    public void update() {
        b = false;
        if (highLightTime <= 0) { isHighLight = false;}
        else { highLightTime --; isHighLight = true; }
    }

    public void action() {
        b = true;
        isHighLight = true;
        highLightTime = highLightCool;
    }

    public String getName() {
        return name;
    }

    public boolean isHighLight() {
        return isHighLight;
    }

    public boolean consumeAction() {
        if (b) {
            b = false; // 확인하는 즉시 초기화
            return true;
        }
        return false;
    }
}
