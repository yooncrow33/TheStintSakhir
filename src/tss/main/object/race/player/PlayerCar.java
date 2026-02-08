package tss.main.object.race.player;

import tss.main.object.race.Lap;
import tss.main.object.track.Bahrain;

import java.util.ArrayList;

public class PlayerCar {
    final Bahrain circuit;
    Bahrain.partState state;
    ArrayList<Lap> laps = new ArrayList<>();
    int currentDistance = 0;

    boolean pit = true;

    public int totalDistance;

    final int lapDistance = 5412;

    public PlayerCar(Bahrain bahrain) {
        circuit = bahrain;
        state = Bahrain.partState.pit;
    }

    public void update() {
        if (currentDistance >= state.getDistance()) {
            state.getNext(pit);
            initPart();
            switch (state) {
                case straight1 -> initLap();
                case turn5snake -> setCurrentSector(1);
                case turn13entry -> setCurrentSector(2);
            }
        }
        calculatePhysics();
        getCurrentLap().tick++;
    }

    public void calculatePhysics() {
         getCurrentLap().distance += state.getDistance() / state.getTick();
    }

    public void driverOut() {
        state = Bahrain.partState.pit;
        laps.add(new Lap());
    }

    public void initLap() {
        getCurrentLap().s3Tick = (getCurrentLap().tick - getCurrentLap().s1Tick) - getCurrentLap().s2TIck;
        getCurrentLap().lapTimeTick = getCurrentLap().tick;
        laps.add(new Lap());
    }

    public void initPart() {
        currentDistance = 0;

    }

    public void updateData() {
        totalDistance = (laps.size() * lapDistance) + getCurrentLap().distance;
        // more data update
    }

    public void setCurrentSector(int s) {
        if (s == 1) {
            getCurrentLap().s1Tick = getCurrentLap().tick;
        } else if (s == 2) {
            getCurrentLap().s2TIck = getCurrentLap().tick - getCurrentLap().s1Tick;
        }
    }

    public Lap getCurrentLap() {
        return laps.get(laps.size() - 1);
    }

    public void init() {
        state = Bahrain.partState.pit ; // 시작은 다시 피트에서
        currentDistance = 0;
        totalDistance = 0;
        laps.clear()     ;             // 기록 싹 비우기
        laps.add(new Lap())  ;              // 첫 랩 다시 넣어주기
        pit = true;
    }
}
