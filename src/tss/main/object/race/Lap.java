package tss.main.object.race;

public class Lap {
    public int tick = 0;       // 현재 랩의 총 경과 시간 (계속 늘어남)
    public int lapTimeTick;

    // 각 섹터 "기록" (끝났을 때만 값이 들어감, 안 끝났으면 0)
    public int s1Tick = 0;
    public int s2Tick = 0;     // 오타 수정 (TIck -> Tick)
    public int s3Tick = 0;

    public int tireLoseHealth = 0;
    public double distance = 0;

    public boolean isSector1ended = false;
    public boolean isSector2ended = false; // 이거 필요함!
    public boolean isSector3ended = false; // 랩 종료 여부

    // 섹터 1: 끝났으면 기록 리턴, 안 끝났으면 그냥 현재 틱
    public int getCurrents1Tick() {
        if (isSector1ended) {
            return s1Tick;
        }
        return tick;
    }

    // 섹터 2: S1 안 끝났으면 0, 끝났으면 (현재 - S1), S2도 끝났으면 기록 리턴
    public int getCurrents2Tick() {
        if (!isSector1ended) return 0; // 아직 S1도 못 옴

        if (isSector2ended) {
            return s2Tick; // S2 끝났음 (고정값)
        }
        return tick - s1Tick; // S2 달리는 중 (현재 - S1기록)
    }

    // 섹터 3: S2 안 끝났으면 0, 끝났으면 (현재 - S1 - S2)
    public int getCurrents3Tick() {
        if (!isSector2ended) return 0; // 아직 S2도 못 옴

        // S3는 보통 랩이 끝나는 거라 tick 자체가 고정되겠지만, 로직상으로는:
        if (isSector3ended) {
            return s3Tick;
        }
        return tick - s1Tick - s2Tick; // S3 달리는 중
    }
}