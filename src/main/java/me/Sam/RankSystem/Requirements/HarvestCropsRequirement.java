package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankSystem;
import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class HarvestCropsRequirement extends RankupRequirement {
    public HarvestCropsRequirement(int requirement) {
        super(requirement, "Crops Harvested");
    }

    @Override
    public int getCurrent(Player player) {
        return RankSystem.playerStatsMap.get(player.getUniqueId()).getCropsHarvested();
    }
}
