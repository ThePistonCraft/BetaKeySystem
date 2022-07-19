package de.coooding.betakey.utils;

import de.coooding.betakey.BetaKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class KeyProvider {

    public void addBetaKey(String betaKey) {
        final File file = new File("plugins/BetaKeySystem/betaKey.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()) {
            try {
                file.createNewFile();
                cfg.save(file);
            } catch (IOException ignored) { }
        }

        final List<String> keys = (List<String>) cfg.getStringList("betaKeys");

        keys.add(betaKey);
        cfg.set("betaKeys", keys);

        try {
            cfg.save(file);
        } catch (IOException ignored) { }
    }

    public void removeBetaKey(String betaKey) {
        final File file = new File("plugins/BetaKeySystem/betaKey.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()) {
            try {
                file.createNewFile();
                cfg.save(file);
            } catch (IOException ignored) { }
        }

        final List<String> keys = (List<String>) cfg.getStringList("betaKeys");

        keys.remove(betaKey);
        cfg.set("betaKeys", keys);

        try {
            cfg.save(file);
        } catch (IOException ignored) { }
    }

    public boolean existBetaKey(String betaKey) {
        final File file = new File("plugins/BetaKeySystem/betaKey.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        final List<String> keys = (List<String>) cfg.getStringList("betaKeys");

        if(keys.contains(betaKey))
            return true;

        return false;
    }

    public void addPlayerBeta(UUID playerUUID) {
        final File file = new File("plugins/BetaKeySystem/betaPlayer.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()) {
            try {
                file.createNewFile();
                cfg.save(file);
            } catch (IOException ignored) { }
        }

        final List<String> betaPlayers = (List<String>) cfg.getStringList("betaPlayer");

        betaPlayers.add(playerUUID.toString());
        cfg.set("betaPlayer", betaPlayers);


        try {
            cfg.save(file);
        } catch (IOException ignored) { }
    }

    public void removePlayerBeta(UUID playerUUID) {
        final File file = new File("plugins/BetaKeySystem/betaPlayer.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()) {
            try {
                file.createNewFile();
                cfg.save(file);
            } catch (IOException ignored) { }
        }

        final List<String> betaPlayers = (List<String>) cfg.getStringList("betaPlayer");

        betaPlayers.remove(playerUUID.toString());
        cfg.set("betaPlayer", betaPlayers);

        try {
            cfg.save(file);
        } catch (IOException ignored) { }
    }

    public boolean isPlayerInBeta(UUID betaPlayerUUID) {
        final File file = new File("plugins/BetaKeySystem/betaPlayer.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        final List<String> betaPlayers = (List<String>) cfg.getStringList("betaPlayer");

        if(betaPlayers.contains(betaPlayerUUID.toString()))
            return true;

        return false;
    }

    public void setBeta(boolean type) {
        BetaKey.getInstance().getConfig().set("Settings.enable", type);
    }

    public boolean isBeta() {
        if(BetaKey.getInstance().getConfig().getBoolean("Settings.enable"))
            return true;
        return false;
    }
}
