package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class WitherKillsRequirement extends RankupRequirement {
    public WitherKillsRequirement(int requirement) {
        super(requirement, "Withers Killed");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER);
    }
}
