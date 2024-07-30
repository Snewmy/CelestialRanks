package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.GUI;
import me.Sam.RankSystem.Locale;
import me.Sam.RankSystem.RankManager;
import me.Sam.RankSystem.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(Utils.chat(Locale.instance.getNoPrefix("title")))) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            RankManager rankManager = new RankManager();
            ItemStack itemStack = event.getCurrentItem();
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat(Locale.instance.getNoPrefix("completedrankname")))) {
                player.sendMessage(Utils.chat(Locale.instance.get("alreadycompleted")));
                return;
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat(Locale.instance.getNoPrefix("inprogressrankname")))) {
                if (!rankManager.canRankup(player)) {
                    player.sendMessage(Utils.chat(Locale.instance.get("donotmeetrequirements")));
                    return;
                }
                rankManager.rankUp(player);
                player.sendMessage(Utils.chat(Locale.instance.get("successfullyrankedup")));
                GUI gui = new GUI();
                gui.createGUI(player);
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat(Locale.instance.getNoPrefix("lockedrankname")))) {
                player.sendMessage(Utils.chat(Locale.instance.get("cannotunlockyet")));
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat(Locale.instance.getNoPrefix("backtoranksname")))) {
                Bukkit.dispatchCommand(player, "ranks");
            }
        }
    }
}
