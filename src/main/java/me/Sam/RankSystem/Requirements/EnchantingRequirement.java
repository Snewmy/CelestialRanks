package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class EnchantingRequirement extends RankupRequirement {
    public EnchantingRequirement(int requirement) {
        super(requirement, "Items Enchanted");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.ITEM_ENCHANTED);
    }
}
