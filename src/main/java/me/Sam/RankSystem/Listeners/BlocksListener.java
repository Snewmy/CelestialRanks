package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.RankSystem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlocksListener implements Listener {

    RankSystem main;

    public BlocksListener(RankSystem main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        switch (event.getBlock().getType()) {
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                handleStats(event.getPlayer(), event.getBlock());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlace(BlockPlaceEvent event) {
        switch (event.getBlock().getType()) {
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                Block block = event.getBlock();
                block.setMetadata("placed", new FixedMetadataValue(main, "placed"));
        }
    }

    public void handleStats(Player player, Block block) {
        if (block.hasMetadata("placed")) {
            return;
        }
        PlayerStats playerStats = RankSystem.playerStatsMap.get(player.getUniqueId());
        switch (block.getType()) {
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                playerStats.setDiamondsMined(playerStats.getDiamondsMined() + 1);
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                playerStats.setEmeraldsMined(playerStats.getEmeraldsMined() + 1);
        }
    }
}
