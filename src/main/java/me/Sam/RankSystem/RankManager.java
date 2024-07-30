package me.Sam.RankSystem;

import com.bencodez.votingplugin.topvoter.TopVoter;
import com.bencodez.votingplugin.user.UserManager;
import me.Sam.RankSystem.Events.RankupEvent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;

public class RankManager {

    public RankManager() {
    }

    public void rankDown(Player p) {
        PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
        playerStats.setRank(getRankBelow(playerStats));
    }

    public Rank getRankBelow(PlayerStats playerStats) {
        for (Map.Entry<String, Rank> entry : RankSystem.ranks.entrySet()) {
            if (entry.getValue().getPosition() == (playerStats.getRank().getPosition() - 1)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void rankUp(Player p) {
        PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
        for (RankupRequirement requirement : playerStats.getRank().getRequirements()) {
            requirement.onRankup(p);
        }
        playerStats.setRank(getNextRank(playerStats));
        Rank rank = playerStats.getRank();
        RankupEvent rankupEvent = new RankupEvent(p, rank);
        Bukkit.getPluginManager().callEvent(rankupEvent);
        sendRewardCommands(p, rank);
    }

    public void rankUpNoWithdraw(Player p) {
        PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
        playerStats.setRank(getNextRank(playerStats));
        Rank rank = playerStats.getRank();
        RankupEvent rankupEvent = new RankupEvent(p, rank);
        Bukkit.getPluginManager().callEvent(rankupEvent);
        sendRewardCommands(p, rank);
    }

    public Rank getPlayerRankInProgress(Player p) {
        return getNextRank(RankSystem.playerStatsMap.get(p.getUniqueId()));
    }

    public boolean canRankup(Player p) {
        PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
        if (playerStats.getRank().isLastRank()) {
            return false;
        }
        Rank nextRank = getNextRank(playerStats);
        for (RankupRequirement requirement : nextRank.getRequirements()) {
            if (!requirement.isPassed(p, nextRank)) {
                return false;
            }
        }
        return true;
    }

    private void sendRewardCommands(Player p, Rank rank) {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        for (String command : rank.getRewardCommands()) {
            Bukkit.dispatchCommand(console, command.replace("%player%", p.getName()));
        }
    }

    private Rank getNextRank(PlayerStats playerStats) {
        int currentRankPosition = playerStats.getRank().getPosition();
        int nextRankPosition = currentRankPosition + 1;
        for (Map.Entry<String, Rank> entry : RankSystem.ranks.entrySet()) {
            Rank rank = entry.getValue();
            if (rank.getPosition() == nextRankPosition) {
                return rank;
            }
        }
        return null;
    }
}
