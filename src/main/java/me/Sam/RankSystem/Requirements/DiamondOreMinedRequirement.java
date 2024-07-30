package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankSystem;
import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class DiamondOreMinedRequirement extends RankupRequirement {
    public DiamondOreMinedRequirement(int requirement) {
        super(requirement, "Diamond Ore Mined");
    }

    @Override
    public int getCurrent(Player player) {
        return RankSystem.playerStatsMap.get(player.getUniqueId()).getDiamondsMined();
    }
}
