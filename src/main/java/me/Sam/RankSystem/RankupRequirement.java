package me.Sam.RankSystem;

import org.bukkit.entity.Player;

public abstract class RankupRequirement {
    public int requirement;
    public String name;

    public RankupRequirement(int requirement, String name) {
        this.requirement = requirement;
        this.name = name;
    }

    public String isPassedString(Player player) {
        if (getCurrent(player) >= getRequirement()) {
            return Locale.instance.getNoPrefix("passedstring");
        } else {
            return Locale.instance.getNoPrefix("notpassedstring").replace("%current%", getCurrent(player) + "");
        }
    }

    public abstract int getCurrent(Player player);
    public int getRequirement() {
        return this.requirement;
    }

    public boolean isPassed(Player player, Rank rank) {
        return getCurrent(player) >= getRequirement();
    }

    public String getName() {
        return this.name;
    }

    public void onRankup(Player player) {}
}
