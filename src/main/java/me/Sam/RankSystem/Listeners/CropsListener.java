package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.RankManager;
import me.Sam.RankSystem.RankSystem;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CropsListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) event.getBlock().getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge()) {
                PlayerStats playerStats = RankSystem.playerStatsMap.get(event.getPlayer().getUniqueId());
                playerStats.setCropsHarvested(playerStats.getCropsHarvested() + 1);
            }
        }
    }
}
