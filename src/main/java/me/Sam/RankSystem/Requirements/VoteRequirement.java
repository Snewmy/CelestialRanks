package me.Sam.RankSystem.Requirements;

import com.bencodez.votingplugin.VotingPluginHooks;
import com.bencodez.votingplugin.topvoter.TopVoter;
import com.bencodez.votingplugin.user.UserManager;
import me.Sam.RankSystem.RankupRequirement;
import org.bukkit.entity.Player;

public class VoteRequirement extends RankupRequirement {

    public VoteRequirement(int requirement) {
        super(requirement, "Votes");
    }

    @Override
    public int getCurrent(Player player) {
        return VotingPluginHooks.getInstance().getUserManager().getVotingPluginUser(player).getTotal(TopVoter.AllTime);
    }
}
