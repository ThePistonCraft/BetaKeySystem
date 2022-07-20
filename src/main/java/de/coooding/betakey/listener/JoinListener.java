package de.coooding.betakey.listener;

import de.coooding.betakey.BetaKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class JoinListener implements Listener {
    public static ArrayList<Player> inputKey = new ArrayList<>();

    int timeOutSeconds = BetaKey.getInstance().getConfig().getInt("Settings.playerTimeout");
    int task;

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if(BetaKey.getInstance().getKeyProvider().isBeta()) {
            if(!BetaKey.getInstance().getKeyProvider().isPlayerInBeta(player.getUniqueId())) {
                inputKey.add(player);

                player.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 999999999, 1, false, false)));
                player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                        BetaKey.getInstance().getConfig().getString("Messages.notInBeta").replaceAll("&", "§"));

                task = BetaKey.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) BetaKey.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        String kickReason = BetaKey.getInstance().getConfig().getString("Messages.kickTimeout").replaceAll("&", "§");
                        player.kickPlayer(kickReason);
                        BetaKey.getInstance().getServer().getScheduler().cancelTask(task);
                    }
                }, 0L, 20L * timeOutSeconds);
                return;
            }
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }

    }
}
