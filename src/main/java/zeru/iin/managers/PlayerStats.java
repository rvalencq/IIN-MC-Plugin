package zeru.iin.managers;

public class PlayerStats {
    private final int playedTime;
    private final int minedBlocks;

    public PlayerStats(int playedTime, int minedBlocks) {
        this.playedTime = playedTime;
        this.minedBlocks = minedBlocks;
    }

    public int getPlayedTime() {
        return playedTime;
    }
    public int getMinedBlocks() {
        return minedBlocks;
    }
}
