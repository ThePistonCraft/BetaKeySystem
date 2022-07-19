package de.coooding.betakey.listener;

import de.coooding.betakey.BetaKey;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class ChatListener implements Listener {

    int invalidUses = 0;
    int task;

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        if(JoinListener.inputKey.contains(player)) {
            event.setCancelled(true);
            String message = event.getMessage();

            if(!BetaKey.getInstance().getKeyProvider().existBetaKey(message)) {
                player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                        BetaKey.getInstance().getConfig().getString("Messages.keyNotExist"));

                invalidUses++;

                if(invalidUses == 3) {
                    Bukkit.getScheduler().runTask(BetaKey.getInstance(), new Runnable() {
                        public void run() {
                            String kickReason = BetaKey.getInstance().getConfig().getString("Messages.kickOverdrawn").replaceAll("&", "§");
                            player.kickPlayer(kickReason);
                        }
                    });
                }
                return;
            }

            BetaKey.getInstance().getKeyProvider().addPlayerBeta(player.getUniqueId());
            BetaKey.getInstance().getKeyProvider().removeBetaKey(message);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.keyUsed").replaceAll("&", "§"));

            JoinListener.inputKey.remove(player);
        }

        task = BetaKey.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) BetaKey.getInstance(), new Runnable() {
            @Override
            public void run() {
                event.setCancelled(false);
                BetaKey.getInstance().getServer().getScheduler().cancelTask(task);
            }
        }, 0L, 100L);
    }
}
