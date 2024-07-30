package me.Sam.RankSystem.Commands;

import me.Sam.RankSystem.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankup")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                GUI gui = new GUI();
                gui.createGUI(player);
            }
        }
        return false;
    }
}
