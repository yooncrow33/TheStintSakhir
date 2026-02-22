package tss.main.object.track;

public class Bahrain {
    static int t10s = 63;
    static int t05s = 31;
    static int t01s = 6;
    public enum partState {
        straight1(575, t10s*6 - 3,"turn1entry", 0.0000,0.0000,0.0000),
        turn1entry(148, t10s*4 + t05s - 3,"turn2entry", 0.0000,0.0000,0.0000), //10.5 // 723
        turn2entry(115, t10s*2 + t05s + t01s,"turn3exit", 0.0000,0.0000,0.0000), // 13.1 838
        turn3exit(152, t10s*2 + t05s + t01s*4 - 6,"drs1", 0.0000,0.0000,0.0000), // 15.9 990
        drs1(410,t10s*5 + t05s - 2,"turn4entry", 0.0000,0.0000,0.0000), // 21.4 1400
        turn4entry(100, t10s*2 + t05s + 12,"turn4exit", 0.0000,0.0000,0.0000), //24.1 1500
        turn4exit(300, t10s*4 + t05s + t01s - 8,"turn5snake", 0.0000,0.0000,0.0000), //28.6 1800 s1 end 5tick오차(24시즌 베르스타펜의 폴랩.)

        turn5snake(176,t10s*4 + t01s * 4 + 5,"turn7exit", 0.0000,0.0000,0.0000), //33.1 1976
        turn7exit(124, t05s + t01s*4 + 1,"turn8entry", 0.0000,0.0000,0.0000), //34.0 2100
        turn8entry(130, t10s*3 - t01s - 2,"turn8exit", 0.0000,0.0000,0.0000), //36.9 2230
        turn8exit(100, t10s*3 - t01s*3 - 2,"straight2", 0.0000,0.0000,0.0000), //39.6 2330
        straight2(230, t10s*4 + t01s*2 - 1,"turn10entry", 0.0000,0.0000,0.0000), //43.8 2560
        turn10entry(130, t10s*3 + t01s*2 - 1,"turn10exit", 0.0000,0.0000,0.0000), //47.0 2690
        turn10exit(50, t10s*2 + t01s - 1,"drs2", 0.0000,0.0000,0.0000), //49.1 2740
        drs2(540, t10s*7 + t05s + t01s*2 - 3,"turn11entry", 0.0000,0.0000,0.0000), //56.8 3280
        turn11entry(140, t10s*2 + t01s*4,"turn11exit", 0.0000,0.0000,0.0000), //59.2 3420
        turn11exit(110, t10s + t01s*3,"straight3", 0.0000,0.0000,0.0000), //1:00.5  3530
        straight3(170,t10s*2 + t05s + t01s,"turn12", 0.0000,0.0000,0.0000), //1:03.1 3700
        turn12(260, t10s*4 + t01s*2 - 20,"turn13entry", 0.0000,0.0000,0.0000), //1:07.0 3960 s2 end  8틱 오차

        turn13entry(116, t10s + t01s,"turn13exit", 0.0000,0.0000,0.0000), //1:08.1 4076
        turn13exit(114, t10s*3 + t01s*3 - 1,"straight4", 0.0000,0.0000,0.0000), //1:11.4 4190
        straight4(576, t10s * 8 - 4,"turn14entry", 0.0000,0.0000,0.0000), //1:19.4 4766
        turn14entry(114, t10s*2 + t01s * 2,"turn14exit", 0.0000,0.0000,0.0000), //1:21.6 4880
        turn14exit(114, t10s*2 + t01s*3,"straight5", 0.0000,0.0000,0.0000), //1:23.9 4994
        straight5(418, t10s*6 + t01s*2 - 60,"straight1", 0.0000,0.0000,0.0000), //1:29.179 5412 s3 end

        pit(575 + 418, t10s*22, "turn1entry",0.0,0.0,0.0);

        //this is default lap time
        private final int distance;
        private final int tick;
        private final String nextPartName;
        private final double fWearWeight;
        private final double rWearWeight;
        private final double fuelWeight;

        // 3. 생성자 (위에서 괄호 안에 넣은 숫자를 여기서 받습니다)
        partState(int distance, int tick, String nextPartName, double fw, double rw, double flw) {
            this.distance = distance;
            this.tick = tick;
            this.nextPartName = nextPartName;
            this.fWearWeight = fw;
            this.rWearWeight = rw;
            this.fuelWeight = flw;
        }

        public partState getNext(boolean isPitting) {
            // 만약 특정 구간(예: turn14)에서 피트인을 결정했다면?
            if (isPitting && this == turn14exit) return pit;

            // 그 외에는 미리 정해진 다음 구간으로 이동
            return partState.valueOf(nextPartName);
        }

        // 4. 데이터를 꺼내 쓸 수 있는 메소드 (Getter)
        public int getDistance() {
            return distance;
        }

        public int getTick() {return tick; }
        public double getFWearWeight() {return fWearWeight; }
        public double getRWearWeight() {return rWearWeight; }
        public double getFuelWeight() {return fuelWeight; }
    }
}
