package me.Sam.RankSystem.TabCompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankupAdminTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completions = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("rankupadmin")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("SamsRankSystem.admin")) {
                    return completions;
                }
                if (args.length == 1) {
                    commands.add("check");
                    commands.add("promote");
                    commands.add("demote");
                    commands.add("reload");
                    StringUtil.copyPartialMatches(args[0], commands, completions);
                } else if (args.length == 2) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        commands.add(player.getName());
                    }
                    StringUtil.copyPartialMatches(args[1], commands, completions);
                }
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
