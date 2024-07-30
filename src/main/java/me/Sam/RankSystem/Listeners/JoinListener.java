package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.Rank;
import me.Sam.RankSystem.RankSystem;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.track.Track;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
            PlayerStats playerStats = new PlayerStats(RankSystem.ranks.get("Rank0"), 0);
            RankSystem.playerStatsMap.put(player.getUniqueId(), playerStats);
        }
        PlayerStats playerStats = RankSystem.playerStatsMap.get(player.getUniqueId());
        if (!hasWrongGroup(player, playerStats)) {
            return;
        }
        RankSystem.instance.getLogger().info("Player " + player.getName() + " is in the wrong group. Clearing rank groups.");
        User user = RankSystem.instance.luckPerms.getUserManager().getUser(player.getUniqueId());
        removeOtherGroups(user);
        RankSystem.instance.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User luckUser) -> {
            Group group = getProperGroup(player, playerStats);
            Node node = InheritanceNode.builder(group).build();
            RankSystem.instance.getLogger().info("Rank: " + playerStats.getRank().getKey() + " Proper group: " + group.getName());
            luckUser.data().add(node);
        });
        RankSystem.instance.luckPerms.getUserManager().saveUser(user);
        RankSystem.instance.getLogger().info("Saving " + player.getName() + ".");
    }

    public boolean hasWrongGroup(Player player, PlayerStats playerStats) {
        if (player.isOp()) {
            return false;
        }
        if (playerStats.getRank().getPosition() == 0 && player.hasPermission("group.rank1")) {
            return true;
        }
        if (playerStats.getRank().getPosition() == 1 && player.hasPermission("group.rank2")) {
            return true;
        } else if (playerStats.getRank().getPosition() == 2 && player.hasPermission("group.rank3")) {
            return true;
        } else if (playerStats.getRank().getPosition() == 3 && player.hasPermission("group.rank4")) {
            return true;
        } else if (playerStats.getRank().getPosition() == 4 && player.hasPermission("group.rank5")) {
            return true;
        } else if (playerStats.getRank().getPosition() == 5 && player.hasPermission("group.rank6")) {
            return true;
        }
        return false;
    }

    public Group getProperGroup(Player player, PlayerStats playerStats) {
        switch (playerStats.getRank().getPosition()) {
            case 0:
                return RankSystem.instance.luckPerms.getGroupManager().getGroup("default");
            case 1:
                return RankSystem.instance.luckPerms.getGroupManager().getGroup("rank1");
            case 2:
                return RankSystem.instance.luckPerms.getGroupManager().getGroup("rank2");
            case 3:
                return RankSystem.instance.luckPerms.getGroupManager().getGroup("rank3");
            case 4:
                return RankSystem.instance.luckPerms.getGroupManager().getGroup("rank4");
            case 5:
                return RankSystem.instance.luckPerms.getGroupManager().getGroup("rank5");
        }
        return RankSystem.instance.luckPerms.getGroupManager().getGroup("default");
    }

    public void removeOtherGroups(User user) {
        for (int i = 1; i < 7; i++) {
            Group group = RankSystem.instance.luckPerms.getGroupManager().getGroup("rank" + i);
            InheritanceNode groupNode = InheritanceNode.builder(group).build();
            DataMutateResult result = user.data().remove(groupNode);
        }
        RankSystem.instance.luckPerms.getUserManager().saveUser(user);
    }
}
