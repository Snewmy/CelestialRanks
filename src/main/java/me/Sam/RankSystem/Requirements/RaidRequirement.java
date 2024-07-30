package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class RaidRequirement extends RankupRequirement {
    public RaidRequirement(int requirement) {
        super(requirement, "Raids Won");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.RAID_WIN);
    }
}
