package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class AnimalsBredRequirement extends RankupRequirement {
    public AnimalsBredRequirement(int requirement) {
        super(requirement, "Animals Bred");
    }

    @Override
    public int getCurrent(Player player) {
        return player.getStatistic(Statistic.ANIMALS_BRED);
    }
}
