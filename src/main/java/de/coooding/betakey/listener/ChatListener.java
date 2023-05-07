package de.coooding.betakey.listener;

import de.coooding.betakey.BetaKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class ChatListener implements Listener {

    int invalidUses = 0;
    int task;
    int kickOverdrawn;
    int removePotion;


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
                    kickOverdrawn = BetaKey.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) BetaKey.getInstance(), new Runnable() {
                        public void run() {
                            String kickReason = BetaKey.getInstance().getConfig().getString("Messages.kickOverdrawn").replaceAll("&", "§");
                            player.kickPlayer(kickReason);
                            BetaKey.getInstance().getServer().getScheduler().cancelTask(removePotion);
                            BetaKey.getInstance().getServer().getScheduler().cancelTask(JoinListener.getTask());
                            BetaKey.getInstance().getServer().getScheduler().cancelTask(kickOverdrawn);
                        }
                    }, 0L, 100L);
                }
                return;
            }

            BetaKey.getInstance().getKeyProvider().addPlayerBeta(player.getUniqueId());
            BetaKey.getInstance().getKeyProvider().removeBetaKey(message);

            removePotion = BetaKey.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) BetaKey.getInstance(), new Runnable() {
                public void run() {
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    BetaKey.getInstance().getServer().getScheduler().cancelTask(JoinListener.getTask());
                    BetaKey.getInstance().getServer().getScheduler().cancelTask(kickOverdrawn);
                    BetaKey.getInstance().getServer().getScheduler().cancelTask(removePotion);

                }
            },0L, 100L);

            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.keyUsed").replaceAll("&", "§"));
            JoinListener.inputKey.remove(player);
        }

        task = BetaKey.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) BetaKey.getInstance(), new Runnable() {
            @Override
            public void run() {
                event.setCancelled(false);
                try {
                    BetaKey.getInstance().getServer().getScheduler().cancelTask(JoinListener.getTask());
                    BetaKey.getInstance().getServer().getScheduler().cancelTask(kickOverdrawn);
                    BetaKey.getInstance().getServer().getScheduler().cancelTask(removePotion);
                } catch (NullPointerException ignored) { }
                BetaKey.getInstance().getServer().getScheduler().cancelTask(task);
            }
        }, 0L, 100L);
    }
}
