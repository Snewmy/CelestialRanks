package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.Events.RankupEvent;
import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.Rank;
import me.Sam.RankSystem.RankSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RankupListener implements Listener {

    @EventHandler
    public void onRankGUIOpen(RankupEvent event) {
        Player player = event.getPlayer();
        Rank rank = event.getRank();
        if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
            PlayerStats playerStats = new PlayerStats(rank, 0);
            RankSystem.playerStatsMap.put(player.getUniqueId(), playerStats);
        }
    }
}
