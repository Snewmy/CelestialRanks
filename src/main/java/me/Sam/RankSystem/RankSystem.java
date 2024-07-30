package me.Sam.RankSystem;

import me.Sam.RankSystem.Commands.RankPrefixCommand;
import me.Sam.RankSystem.Commands.RankupAdminCommand;
import me.Sam.RankSystem.Commands.RankupCommand;
import me.Sam.RankSystem.Listeners.*;
import me.Sam.RankSystem.Requirements.*;
import me.Sam.RankSystem.TabCompleters.RankPrefixTab;
import me.Sam.RankSystem.TabCompleters.RankupAdminTab;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class RankSystem extends JavaPlugin {

    public static Map<String, Rank> ranks = new LinkedHashMap<>();
    public static Map<UUID, PlayerStats> playerStatsMap = new HashMap<>();
    public static RankSystem instance;
    public File dataFile = new File(getDataFolder(), "data.yml");
    public FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
    public ChatPrefixPlaceholder placeholder = null;
    public LuckPerms luckPerms = null;
    public File messagesFile;
    public FileConfiguration messages;
    public static Economy econ = null;

    public void onEnable() {
        instance = this;
        if (!this.setupEconomy()) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }
        saveResource("messages.yml", false);
        messagesFile = new File(getDataFolder(), "messages.yml");
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        loadRanks();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RankupListener(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new CropsListener(), this);
        pm.registerEvents(new BlocksListener(this), this);
        getCommand("rankup").setExecutor(new RankupCommand());
        getCommand("rankupadmin").setExecutor(new RankupAdminCommand());
        getCommand("rankupadmin").setTabCompleter(new RankupAdminTab());
        getCommand("rankprefix").setExecutor(new RankPrefixCommand());
        getCommand("rankprefix").setTabCompleter(new RankPrefixTab());
        if (!data.isConfigurationSection("Data")) {
            data.createSection("Data");
            try {
                data.save(dataFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        loadData();
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                saveData();
                Bukkit.getLogger().info("Saving data");
            }
        }, 30000, 30000);
        if (pm.getPlugin("PlaceholderAPI") != null) {
            placeholder = new ChatPrefixPlaceholder(this);
            placeholder.register();
        }
        new Locale();
    }

    public void onDisable() {
        saveData();
        placeholder.unregister();
    }

    public void loadData() {
        ConfigurationSection dataSection = data.getConfigurationSection("Data");
        for (String uuid : dataSection.getKeys(false)) {
            String rankKey = dataSection.getString(uuid + ".rank");
            int cropsHarvested = dataSection.getInt(uuid + ".cropsharvested");
            PlayerStats playerStats = new PlayerStats(ranks.get(rankKey), cropsHarvested);
            if (dataSection.getString(uuid + ".prefixOn") == null) {
                playerStats.setPrefixOn(true);
            } else {
                playerStats.setPrefixOn(dataSection.getBoolean(uuid + ".prefixOn"));
            }
            int diamondsMined = dataSection.getInt(uuid + ".diamondsmined");
            int emeraldsMined = dataSection.getInt(uuid + ".emeraldsmined");
            playerStats.setCropsHarvested(cropsHarvested);
            playerStats.setDiamondsMined(diamondsMined);
            playerStats.setEmeraldsMined(emeraldsMined);
            playerStatsMap.put(UUID.fromString(uuid), playerStats);
        }
    }

    public void loadRanks() {
        ConfigurationSection ranks = getConfig().getConfigurationSection("Ranks");
        for (String rankKey : ranks.getKeys(false)) {
            String key = ranks.getString(rankKey + ".info.key");
            String name = ranks.getString(rankKey + ".info.name");
            String prefix = ranks.getString(rankKey + ".info.prefix");
            int position = ranks.getInt(rankKey + ".info.position");
            boolean lastRank = ranks.getBoolean(rankKey + ".info.lastrank");
            int guiSlot = ranks.getInt(rankKey + ".info.slot");
            RankupRequirement voteRequirement = new VoteRequirement(ranks.getInt(rankKey + ".info.requirements.vote"));
            RankupRequirement moneyRequirement = new MoneyRequirement(ranks.getInt(rankKey + ".info.requirements.money"));
            RankupRequirement blocksMinedRequirement = new BlocksMinedRequirement(ranks.getInt(rankKey + ".info.requirements.blocksmined"));
            RankupRequirement diamondOreRequirement = new DiamondOreMinedRequirement(ranks.getInt(rankKey + ".info.requirements.diamondsmined"));
            RankupRequirement emeraldOreRequirement = new EmeraldOreMinedRequirement(ranks.getInt(rankKey + ".info.requirements.emeraldsmined"));
            RankupRequirement dragonKillsRequirement = new DragonKillsRequirement(ranks.getInt(rankKey + ".info.requirements.dragonkills"));
            RankupRequirement enchantingRequirement = new EnchantingRequirement(ranks.getInt(rankKey + ".info.requirements.enchants"));
            RankupRequirement fishingRequirement = new FishingRequirement(ranks.getInt(rankKey + ".info.requirements.fishing"));
            RankupRequirement cropsHarvestedRequirement = new HarvestCropsRequirement(ranks.getInt(rankKey + ".info.requirements.cropsharvested"));
            RankupRequirement monstersKilledRequirement = new MonstersKilledRequirement(ranks.getInt(rankKey + ".info.requirements.monsterskilled"));
            RankupRequirement raidRequirement = new RaidRequirement(ranks.getInt(rankKey + ".info.requirements.raids"));
            RankupRequirement villagerTradeRequirement = new VillagerTradesRequirement(ranks.getInt(rankKey + ".info.requirements.villagertrades"));
            RankupRequirement witherKillsRequirement = new WitherKillsRequirement(ranks.getInt(rankKey + ".info.requirements.witherkills"));
            Rank rank = new Rank(key, name, prefix, position, lastRank, guiSlot);

            getServer().getLogger().info("Loading rank [" + rankKey + "] key: " + key + " name: " + name + " prefix: " + prefix + " position:" + position + " lastrank:" + lastRank + " guislot:" + guiSlot);
            getServer().getLogger().info("Requirements:");

            if (rankKey.equalsIgnoreCase("Rank0")) {
                rank.addRequirement(new VoteRequirement(0));
                getServer().getLogger().info("Placeholder (first rank)");
            }

            if (voteRequirement.getRequirement() > 0) {
                rank.addRequirement(voteRequirement);
                getServer().getLogger().info("Votes: " + voteRequirement.getRequirement());
            }
            if (moneyRequirement.getRequirement() > 0) {
                rank.addRequirement(moneyRequirement);
                getServer().getLogger().info("Money: " + moneyRequirement.getRequirement());
            }
            if (blocksMinedRequirement.getRequirement() > 0) {
                rank.addRequirement(blocksMinedRequirement);
                getServer().getLogger().info("BlocksMined: " + blocksMinedRequirement.getRequirement());
            }
            if (diamondOreRequirement.getRequirement() > 0) {
                rank.addRequirement(diamondOreRequirement);
                getServer().getLogger().info("DiamondsMined: " + diamondOreRequirement.getRequirement());
            }
            if (emeraldOreRequirement.getRequirement() > 0) {
                rank.addRequirement(emeraldOreRequirement);
                getServer().getLogger().info("EmeraldsMined: " + emeraldOreRequirement.getRequirement());
            }
            if (dragonKillsRequirement.getRequirement() > 0) {
                rank.addRequirement(dragonKillsRequirement);
                getServer().getLogger().info("DragonKills: " + dragonKillsRequirement.getRequirement());
            }
            if (enchantingRequirement.getRequirement() > 0) {
                rank.addRequirement(enchantingRequirement);
                getServer().getLogger().info("Enchants: " + enchantingRequirement.getRequirement());
            }
            if (fishingRequirement.getRequirement() > 0) {
                rank.addRequirement(fishingRequirement);
                getServer().getLogger().info("Fish: " + fishingRequirement.getRequirement());
            }
            if (cropsHarvestedRequirement.getRequirement() > 0) {
                rank.addRequirement(cropsHarvestedRequirement);
                getServer().getLogger().info("CropsHarvested: " + cropsHarvestedRequirement.getRequirement());
            }
            if (monstersKilledRequirement.getRequirement() > 0) {
                rank.addRequirement(monstersKilledRequirement);
                getServer().getLogger().info("MonsterKills: " + monstersKilledRequirement.getRequirement());
            }
            if (raidRequirement.getRequirement() > 0) {
                rank.addRequirement(raidRequirement);
                getServer().getLogger().info("RaidsWon: " + raidRequirement.getRequirement());
            }
            if (villagerTradeRequirement.getRequirement() > 0) {
                rank.addRequirement(villagerTradeRequirement);
                getServer().getLogger().info("VillagerTrades: " + villagerTradeRequirement.getRequirement());
            }
            if (witherKillsRequirement.getRequirement() > 0) {
                rank.addRequirement(witherKillsRequirement);
                getServer().getLogger().info("WitherKills: " + witherKillsRequirement.getRequirement());
            }
        }

    }

    public void saveData() {
        ConfigurationSection dataSection = data.getConfigurationSection("Data");
        for (Map.Entry<UUID, PlayerStats> entry : playerStatsMap.entrySet()) {
            dataSection.set(entry.getKey().toString() + ".rank", entry.getValue().getRank().getKey());
            dataSection.set(entry.getKey().toString() + ".rankName", entry.getValue().getRank().getName());
            dataSection.set(entry.getKey().toString() + ".prefixOn", entry.getValue().isPrefixOn());
            dataSection.set(entry.getKey().toString() + ".cropsharvested", entry.getValue().getCropsHarvested());
            dataSection.set(entry.getKey().toString() + ".diamondsmined", entry.getValue().getDiamondsMined());
            dataSection.set(entry.getKey().toString() + ".emeraldsmined", entry.getValue().getEmeraldsMined());
        }
        try {
            data.save(dataFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                econ = (Economy)rsp.getProvider();
                return econ != null;
            }
        }
    }
}
