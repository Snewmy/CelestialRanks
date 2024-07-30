package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DragonKillsRequirement extends RankupRequirement {
    public DragonKillsRequirement(int requirement) {
        super(requirement, "Ender Dragon Kills");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDER_DRAGON);
    }
}
