package de.coooding.betakey;

import de.coooding.betakey.commands.*;
import de.coooding.betakey.listener.ChatListener;
import de.coooding.betakey.listener.JoinListener;
import de.coooding.betakey.listener.MoveListener;
import de.coooding.betakey.utils.KeyProvider;
import de.coooding.betakey.utils.YamlConfigurationLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BetaKey extends JavaPlugin {

    @Getter
    public static BetaKey instance;

    @Getter
    public KeyProvider keyProvider;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage("§aThe BetaKeySystem was created by §eCoooding");
        Bukkit.getConsoleSender().sendMessage("§aThe Support §bDiscord§7: §ehttps://discord.gg/kTwd5grCEw");

        this.init();
    }

    private void init() {
        YamlConfigurationLoader.loadConfiguration(this, "config.yml");
        keyProvider = new KeyProvider();

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);

        getCommand("createkey").setExecutor(new CreateKeyCommand());
        getCommand("togglebeta").setExecutor(new ToggleBetaCommand());
        getCommand("removekey").setExecutor(new RemoveKeyCommand());
        getCommand("removebetaplayer").setExecutor(new RemoveBetaPlayerCommand());
        getCommand("addbetaplayer").setExecutor(new AddBetaPlayerCommand());

    }
}
