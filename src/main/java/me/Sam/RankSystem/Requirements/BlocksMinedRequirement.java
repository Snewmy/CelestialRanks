package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class BlocksMinedRequirement extends RankupRequirement {
    public BlocksMinedRequirement(int requirement) {
        super(requirement, "Blocks Mined");
    }

    @Override
    public int getCurrent(Player player) {
        int totalBlocksMined = 0;
        for (Material material : Material.values()) {
            totalBlocksMined += player.getStatistic(Statistic.MINE_BLOCK, material);
        }
        return totalBlocksMined;
    }
}
