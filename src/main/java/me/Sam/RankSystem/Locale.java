package me.Sam.RankSystem;

import java.util.List;

public class Locale {

    public static Locale instance;

    public Locale() {
        instance = this;
    }

    public String get(String configKey) {
        return RankSystem.instance.messages.getString("prefix") + RankSystem.instance.messages.getString(configKey);
    }
    public List<String> getList(String configKey) {
        return RankSystem.instance.messages.getStringList(configKey);
    }

    public String getNoPrefix(String configKey) {
        return RankSystem.instance.messages.getString(configKey);
    }
}
