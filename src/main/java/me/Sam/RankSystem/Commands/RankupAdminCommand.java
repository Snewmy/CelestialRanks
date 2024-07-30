package me.Sam.RankSystem.Commands;

import me.Sam.RankSystem.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RankupAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankupadmin")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("SamsRankSystem.admin")) {
                    return false;
                }
                RankManager rankManager = new RankManager();
                if (args.length == 0) {
                    for (String s : Locale.instance.getList("rankupadmin")) {
                        p.sendMessage(Utils.chat(s));
                    }
                    return false;
                }
                if (args[0].equalsIgnoreCase("check")) {
                    String playerName = args[1];
                    if (Bukkit.getPlayer(playerName) == null) {
                        p.sendMessage(Utils.chat(Locale.instance.get("invalidplayer")));
                        return false;
                    }
                    Player receiver = Bukkit.getPlayer(playerName);
                    PlayerStats playerStats = RankSystem.playerStatsMap.get(receiver.getUniqueId());
                    p.sendMessage(Utils.chat(Locale.instance.get("rankupadminstats1").replace("%name%", receiver.getName())));
                    p.sendMessage(Utils.chat(Locale.instance.get("rankupadminstats2").replace("%rank%", playerStats.getRank().getName())));
                    if (playerStats.getRank().isLastRank()) {
                        p.sendMessage(Utils.chat(Locale.instance.get("rankupadminstatsislastrank")));
                    } else {
                        p.sendMessage(Utils.chat(Locale.instance.get("rankupadminstatsnextrank").replace("%rank%", rankManager.getPlayerRankInProgress(receiver).getName())));
                    }
                    if (playerStats.getRank().getPosition() != 0) {
                        for (RankupRequirement requirement : playerStats.getRank().getRequirements()) {
                            p.sendMessage(Utils.chat(Locale.instance.get("rankupadminstatsrequirements").replace("%name%", requirement.getName()).replace("%value%", requirement.getRequirement() + "").replace("%ispassed%", requirement.isPassedString(p))));
                        }
                    }
                    return false;
                } else if (args[0].equalsIgnoreCase("promote")) {
                    String playerName = args[1];
                    if (Bukkit.getPlayer(playerName) == null) {
                        p.sendMessage(Utils.chat(Locale.instance.get("invalidplayer")));
                        return false;
                    }
                    Player receiver = Bukkit.getPlayer(playerName);
                    if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                        PlayerStats playerStats = RankSystem.playerStatsMap.get(receiver.getUniqueId());
                        if (playerStats.getRank().isLastRank()) {
                            p.sendMessage(Utils.chat(Locale.instance.get("playeralreadymaxrank")));
                            return false;
                        }
                    }
                    rankManager.rankUpNoWithdraw(receiver);
                    p.sendMessage(Utils.chat(Locale.instance.get("forcerankupsuccess").replace("%player%", receiver.getName())));
                    receiver.sendMessage(Utils.chat(Locale.instance.get("forcerankupreceiver").replace("%player%", p.getName())));
                    return false;
                } else if (args[0].equalsIgnoreCase("demote")) {
                    String playerName = args[1];
                    if (Bukkit.getPlayer(playerName) == null) {
                        p.sendMessage(Utils.chat(Locale.instance.get("invalidplayer")));
                        return false;
                    }
                    Player receiver = Bukkit.getPlayer(playerName);
                    if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                        PlayerStats playerStats = RankSystem.playerStatsMap.get(receiver.getUniqueId());
                        if (playerStats.getRank().getPosition() == 0) {
                            p.sendMessage(Utils.chat(Locale.instance.get("alreadylowestrank")));
                            return false;
                        }
                        rankManager.rankDown(receiver);
                        p.sendMessage(Utils.chat(Locale.instance.get("forcerankdownsuccess").replace("%player%", receiver.getName())));
                        receiver.sendMessage(Utils.chat(Locale.instance.get("forcerankdownreceiver").replace("%player%", p.getName())));
                        return false;
                    } else {
                        p.sendMessage(Utils.chat(Locale.instance.get("notfirstrankyet")));
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    long startTime = System.currentTimeMillis();
                    RankSystem.instance.saveData();
                    RankSystem.playerStatsMap.clear();
                    RankSystem.ranks.clear();
                    RankSystem.instance.reloadConfig();
                    RankSystem.instance.loadRanks();
                    RankSystem.instance.loadData();
                    RankSystem.instance.messages = YamlConfiguration.loadConfiguration(RankSystem.instance.messagesFile);
                    long timeTaken = System.currentTimeMillis() - startTime;
                    sender.sendMessage(Utils.chat(Locale.instance.get("reloadmessage").replace("%time%", timeTaken + "")));
                    return false;
                }
            }
        }
        return false;
    }
}
