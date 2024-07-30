package me.Sam.RankSystem;

public class PlayerStats {

    private Rank rank;
    private boolean prefixOn;

    private int cropsHarvested;
    private int diamondsMined;
    private int emeraldsMined;

    public PlayerStats(Rank rank, int cropsHarvested) {
        this.rank = rank;
        this.prefixOn = true;
        this.cropsHarvested = cropsHarvested;
    }

    public void setDiamondsMined(int diamondsMined) {
        this.diamondsMined = diamondsMined;
    }

    public void setEmeraldsMined(int emeraldsMined) {
        this.emeraldsMined = emeraldsMined;
    }

    public void setPrefixOn(boolean prefixOn) {
        this.prefixOn = prefixOn;
    }

    public boolean isPrefixOn() {
        return prefixOn;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getCropsHarvested() {
        return cropsHarvested;
    }

    public void setCropsHarvested(int cropsHarvested) {
        this.cropsHarvested = cropsHarvested;
    }

    public int getDiamondsMined() {
        return diamondsMined;
    }

    public int getEmeraldsMined() {
        return emeraldsMined;
    }
}
