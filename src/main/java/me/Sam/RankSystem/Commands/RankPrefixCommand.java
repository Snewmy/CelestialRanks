package me.Sam.RankSystem.Commands;

import me.Sam.RankSystem.Locale;
import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.RankSystem;
import me.Sam.RankSystem.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankPrefixCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankprefix")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length < 1) {
                    player.sendMessage(Utils.chat(Locale.instance.get("rankprefixspecify")));
                    return false;
                }
                if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                    player.sendMessage(Utils.chat(Locale.instance.get("rankupfirst")));
                    return false;
                }
                PlayerStats playerStats = RankSystem.playerStatsMap.get(player.getUniqueId());
                if (args[0].equalsIgnoreCase("on")) {
                    if (playerStats.isPrefixOn()) {
                        player.sendMessage(Utils.chat(Locale.instance.get("prefixalreadyon")));
                        return false;
                    }
                    playerStats.setPrefixOn(true);
                    player.sendMessage(Utils.chat(Locale.instance.get("turnedprefixon")));
                    return false;
                } else if (args[0].equalsIgnoreCase("off")) {
                    if (!playerStats.isPrefixOn()) {
                        player.sendMessage(Utils.chat(Locale.instance.get("prefixalreadyoff")));
                        return false;
                    }
                    playerStats.setPrefixOn(false);
                    player.sendMessage(Utils.chat(Locale.instance.get("turnedprefixoff")));
                    return false;
                }
            }
        }
        return false;
    }
}
