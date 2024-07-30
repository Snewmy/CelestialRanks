package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class VillagerTradesRequirement extends RankupRequirement {
    public VillagerTradesRequirement(int requirement) {
        super(requirement, "Villager Trades");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.TRADED_WITH_VILLAGER);
    }
}
