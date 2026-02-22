package tss.main.object.race.player;

import tss.main.object.race.Lap;
import tss.main.object.race.display.Display;
import tss.main.object.track.Bahrain;

import java.util.ArrayList;

public class PlayerCar {
    final Bahrain circuit;
    final Display display;
    Bahrain.partState state;
    ArrayList<Lap> laps = new ArrayList<>();
    double currentDistance = 0;

    boolean pit = false;

    public double totalDistance;

    final double lapDistance = 5412;

    Lap emptyLap = new Lap();

    enum CarState {
        IN_GARAGE,  // 차고 안
        PIT_OUTING, // 피트 나가는 중
        ON_TRACK,   // 서킷 주행 중
        PIT   // 피트 들어오는 중
    }

    CarState carState = CarState.IN_GARAGE;

    public PlayerCar(Bahrain bahrain, Display display) {
        circuit = bahrain;
        this.display = display;
        state = Bahrain.partState.pit;
    }

    public void update() {
        resetActionPlag();
        if (!isRun()) return;
        if (currentDistance >= state.getDistance()) {
            state = state.getNext(pit);
            if (state == Bahrain.partState.pit) carState = CarState.PIT;
            initPart();
            switch (state) {
                case turn1entry -> carState = CarState.ON_TRACK;
                case straight1 -> initLap();
                case turn5snake -> setCurrentSector(1);
                case turn13entry -> setCurrentSector(2);
            }
        }
        calculatePhysics();
        getCurrentLap().tick++;
        System.out.println(getCurrentLap().tick + "/" + laps.size());
        updateData();
    }

    public void calculatePhysics() {
         getCurrentLap().distance += (double) state.getDistance() / state.getTick();
         currentDistance += (double) state.getDistance() / state.getTick();
    }

    public void resetActionPlag() {
        if (display.getScreen().getEnv().getActions().get(0).consumeAction()) {
            if (!(state == Bahrain.partState.pit) || isDriverOuting()) return;
            driverOut();
        }
    }

    public void driverOut() {
        state = Bahrain.partState.pit;
        laps.add(new Lap());
        carState = CarState.PIT_OUTING;
    }

    public void initLap() {
        getCurrentLap().s3Tick = (getCurrentLap().tick - getCurrentLap().s1Tick) - getCurrentLap().s2Tick;
        getCurrentLap().lapTimeTick = getCurrentLap().tick;
        getCurrentLap().isSector3ended = true;
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
            getCurrentLap().isSector1ended = true;
        } else if (s == 2) {
            getCurrentLap().s2Tick = getCurrentLap().tick - getCurrentLap().s1Tick;
            getCurrentLap().isSector2ended = true;
        }
    }

    public Lap getCurrentLap() {
        if (laps.isEmpty()) {
            return emptyLap;
        }
        return laps.get(laps.size() - 1);
    }

    public Lap getLastLap() {
        if (laps.size() <= 1) {
            return getCurrentLap();
        } else {
            return laps.get(laps.size() - 2);
        }
    }

    public Lap getOnDisplayLap() {
        if (getCurrentLap().isSector1ended) return getCurrentLap();
        else return getLastLap();
    }

    public int getFastestLap() {
        if (laps == null || laps.isEmpty()) return 0;

        // 첫 번째 데이터를 기준점으로 잡으면 990000 같은 임의의 숫자가 필요 없어요.
        int fastestTick = laps.get(0).tick;

        for (int i = 1; i < laps.size(); i++) {
            int currentTick = laps.get(i).tick;
            if (currentTick < fastestTick) {
                fastestTick = currentTick;
            }
        }
        return fastestTick;
    }

    public void init() {
        state = Bahrain.partState.pit ; // 시작은 다시 피트에서
        currentDistance = 0;
        totalDistance = 0;
        laps.clear()     ;             // 기록 싹 비우기
        pit = false;

    }

    public void exit() {

    }

    boolean isRun() {
        return !(carState == CarState.IN_GARAGE);
    }
    boolean isDriverOuting() {
        return carState == CarState.PIT_OUTING;
    }
}
