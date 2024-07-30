package me.Sam.RankSystem;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rank {

    private String key;
    private String name;
    private String chatPrefix;
    private int position;
    private boolean lastRank;
    private List<String> rewardCommands;
    private List<String> rewardList;
    private ArrayList<RankupRequirement> requirements = new ArrayList<>();
    private int guiSlot;

    public Rank(String key, String name, String chatPrefix, int position, boolean lastRank, int guiSlot) {
        this.name = name;
        this.chatPrefix = chatPrefix;
        this.position = position;
        this.lastRank = lastRank;
        this.key = key;
        this.guiSlot = guiSlot;
        FileConfiguration config = RankSystem.instance.getConfig();
        if (config.getStringList("Ranks." + key + ".RewardList") == null) {
            List<String> rewardList = new ArrayList<>();
            config.set("Ranks." + key + ".RewardList", rewardList);
            RankSystem.instance.saveConfig();
        }
        if (config.getStringList("Ranks." + key + ".RewardCommands") == null) {
            List<String> rewardCommands = new ArrayList<>();
            config.set("Ranks." + key + ".RewardCommands", rewardCommands);
            RankSystem.instance.saveConfig();
        }
        this.rewardList = config.getStringList("Ranks." + key + ".RewardList");
        this.rewardCommands = config.getStringList("Ranks." + key + ".RewardCommands");
        RankSystem.instance.saveConfig();
        RankSystem.ranks.put(key, this);
    }

    public void addRequirement(RankupRequirement requirement) {
        this.requirements.add(requirement);
    }

    public int getGuiSlot() {
        return guiSlot;
    }

    public ArrayList<RankupRequirement> getRequirements() {
        return this.requirements;
    }

    public boolean isLastRank() {
        return lastRank;
    }

    public int getPosition() {
        return position;
    }

    public List<String> getRewardCommands() {
        return rewardCommands;
    }

    public List<String> getRewardList() {
        return rewardList;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public void setRewardCommands(List<String> list) {
        this.rewardCommands = list;
    }

    public void setRewardList(List<String> list) {
        this.rewardList = list;
    }
}
