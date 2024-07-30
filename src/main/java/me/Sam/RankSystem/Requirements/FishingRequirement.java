package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class FishingRequirement extends RankupRequirement {
    public FishingRequirement(int requirement) {
        super(requirement, "Fish Caught");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.FISH_CAUGHT);
    }
}
