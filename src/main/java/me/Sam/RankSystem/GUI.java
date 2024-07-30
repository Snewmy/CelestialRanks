package me.Sam.RankSystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI {

    RankManager rankManager;

    //tesdt
    public GUI() {
        this.rankManager = new RankManager();
    }

    public void createGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, RankSystem.instance.getConfig().getInt("gui.size"), Utils.chat(Locale.instance.getNoPrefix("title")));
        ItemStack filler = new ItemStack(Material.valueOf(Locale.instance.getNoPrefix("glasspaneitem")), 1);
        ItemMeta itemMeta = filler.getItemMeta();
        itemMeta.setDisplayName(" ");
        filler.setItemMeta(itemMeta);
        ItemStack back = new ItemStack(Material.ARROW, 1);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(Utils.chat(Locale.instance.getNoPrefix("backtoranksname")));
        back.setItemMeta(backMeta);
        inventory.setItem(RankSystem.instance.getConfig().getInt("gui.backbuttonslot"), back);
        for (Map.Entry<String, Rank> entry : RankSystem.ranks.entrySet()) {
            Rank rank = entry.getValue();
            if (rank.getPosition() == 0) {
                continue;
            }
            inventory.setItem(rank.getGuiSlot(), addRank(player, rank));
        }
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, filler);
            }
        }
        player.openInventory(inventory);
    }

    public ItemStack addRank(Player player, Rank rank) {
        ItemStack itemStack;
        switch (getPlayerButtonType(player, rank)) {
            case RANKCOMPLETED:
                itemStack = new ItemStack(Material.valueOf(RankSystem.instance.getConfig().getString("gui.completedbutton.type")), 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Utils.chat(Locale.instance.getNoPrefix("completedrankname")));
                List<String> lore = new ArrayList<>();
                for (String s : Locale.instance.getList("completedranklore")) {
                    lore.add(Utils.chat(s.replace("%rankprefix%", rank.getChatPrefix())));
                }
                for (String rewardLine : rank.getRewardList()) {
                    String line = rewardLine.contains("%noprefix%") ? Locale.instance.getNoPrefix("completedrankrewards").replace("%reward%", rewardLine).replace("%noprefix%", "") : Locale.instance.getNoPrefix("completedrankrewardprefix") + Locale.instance.getNoPrefix("completedrankrewards").replace("%reward%", rewardLine);
                    lore.add(Utils.chat(line));
                }
                itemMeta.setLore(lore);
                boolean glow = RankSystem.instance.getConfig().getBoolean("gui.completedbutton.glow");
                if (glow) {
                    itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                itemStack.setItemMeta(itemMeta);
                break;
            case RANKINPROGRESS:
                itemStack = new ItemStack(Material.valueOf(RankSystem.instance.getConfig().getString("gui.inprogressbutton.type")), 1);
                ItemMeta itemMeta2 = itemStack.getItemMeta();
                itemMeta2.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                itemMeta2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta2.setDisplayName(Utils.chat(Locale.instance.getNoPrefix("inprogressrankname")));
                List<String> lore2 = new ArrayList<>();
                if (rankManager.canRankup(player)) {
                    lore2.add(Utils.chat(Locale.instance.getNoPrefix("inprogressrankcanrankup")));
                }
                for (String s : Locale.instance.getList("inprogressranklore1")) {
                    lore2.add(Utils.chat(s.replace("%rankprefix%", rank.getChatPrefix())));
                }
                for (RankupRequirement requirement : rank.getRequirements()) {
                    lore2.add(Utils.chat(Locale.instance.getNoPrefix("inprogressrankrequirements").replace("%name%", requirement.getName()).replace("%value%", requirement.getRequirement() + "").replace("%ispassed%", requirement.isPassedString(player))));
                }
                for (String s : Locale.instance.getList("inprogressranklore2")) {
                    lore2.add(Utils.chat(s));
                }
                for (String rewardLine : rank.getRewardList()) {
                    String line = rewardLine.contains("%noprefix%") ? Locale.instance.getNoPrefix("inprogressrankrewards").replace("%reward%", rewardLine).replace("%noprefix%", "") : Locale.instance.getNoPrefix("inprogressrankrewardprefix") + Locale.instance.getNoPrefix("inprogressrankrewards").replace("%reward%", rewardLine);
                    lore2.add(Utils.chat(line));
                }
                itemMeta2.setLore(lore2);
                boolean glow2 = RankSystem.instance.getConfig().getBoolean("gui.inprogressbutton.glow");
                if (glow2) {
                    itemMeta2.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                    itemMeta2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                itemStack.setItemMeta(itemMeta2);
                break;
            case RANKLOCKED:
                itemStack = new ItemStack(Material.valueOf(RankSystem.instance.getConfig().getString("gui.lockedbutton.type")), 1);
                ItemMeta itemMeta3 = itemStack.getItemMeta();
                itemMeta3.setDisplayName(Utils.chat(Locale.instance.getNoPrefix("lockedrankname")));
                List<String> lore3 = new ArrayList<>();
                for (String s : Locale.instance.getList("lockedranklore")) {
                    lore3.add(Utils.chat(s.replace("%rankprefix%", rank.getChatPrefix())));
                }
                for (String rewardLine : rank.getRewardList()) {
                    String line = rewardLine.contains("%noprefix%") ? Locale.instance.getNoPrefix("lockedrankrewards").replace("%reward%", rewardLine).replace("%noprefix%", "") : Locale.instance.getNoPrefix("lockedrankrewardprefix") + Locale.instance.getNoPrefix("lockedrankrewards").replace("%reward%", rewardLine);
                    lore3.add(Utils.chat(line));
                }
                itemMeta3.setLore(lore3);
                boolean glow3 = RankSystem.instance.getConfig().getBoolean("gui.lockedbutton.glow");
                if (glow3) {
                    itemMeta3.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                    itemMeta3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                itemStack.setItemMeta(itemMeta3);
                break;
            default:
                //this will never happen lmao
                itemStack = new ItemStack(Material.STICK, 1);
        }
        return itemStack;
    }

    public ButtonType getPlayerButtonType(Player player, Rank rank) {
        if (rankManager.getPlayerRankInProgress(player) == null) {
            return ButtonType.RANKCOMPLETED;
        }
        if (rankManager.getPlayerRankInProgress(player).getPosition() == rank.getPosition()) {
            return ButtonType.RANKINPROGRESS;
        }
        if (RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
            PlayerStats playerStats = RankSystem.playerStatsMap.get(player.getUniqueId());
            if (rank.getPosition() <= playerStats.getRank().getPosition()) {
                return ButtonType.RANKCOMPLETED;
            } else if (rank.getPosition() > rankManager.getPlayerRankInProgress(player).getPosition()) {
                return ButtonType.RANKLOCKED;
            }
        }
        return ButtonType.RANKLOCKED;
    }

}
