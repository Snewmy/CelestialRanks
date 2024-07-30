package me.Sam.RankSystem.Requirements;

import me.Sam.RankSystem.RankSystem;
import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.entity.Player;

public class MoneyRequirement extends RankupRequirement {

    public MoneyRequirement(int requirement) {
        super(requirement, "Money");
    }

    @Override
    public int getCurrent(Player player) {
        return (int) RankSystem.econ.getBalance(player);
    }

    @Override
    public void onRankup(Player player) {
        RankSystem.econ.withdrawPlayer(player, requirement);
    }
}