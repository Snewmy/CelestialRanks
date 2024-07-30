package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class MonstersKilledRequirement extends RankupRequirement {
    public MonstersKilledRequirement(int requirement) {
        super(requirement, "Monsters Killed");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.MOB_KILLS);
    }
}
