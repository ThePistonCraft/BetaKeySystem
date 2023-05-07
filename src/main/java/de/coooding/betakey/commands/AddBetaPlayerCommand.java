package de.coooding.betakey.commands;

import de.coooding.betakey.BetaKey;
import de.coooding.betakey.listener.JoinListener;
import de.coooding.betakey.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class AddBetaPlayerCommand  implements CommandExecutor {
    int task;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        final Player player = (Player) sender;

        if (!player.hasPermission("betakey.addplayer") || !player.hasPermission("betakey.admin")) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.noperm").replaceAll("&", "§"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.commandUse").replaceAll("&", "§").replaceAll("%command%", "/addbetaplayer <player>"));
            return true;
        }

        String playerName = args[0];
        Player target = Bukkit.getPlayer(playerName);
        if(target == null) {
            BetaKey.getInstance().getKeyProvider().addPlayerBeta(UUIDFetcher.getUUID(playerName));
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.addedOfflineBetaPlayer").replaceAll("&", "§"));

            return true;
        }

        BetaKey.getInstance().getKeyProvider().addPlayerBeta(UUIDFetcher.getUUID(playerName));

        player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                BetaKey.getInstance().getConfig().getString("Messages.addBetaPlayer").replaceAll("&", "§").replaceAll("%playerName%", playerName));

        player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                BetaKey.getInstance().getConfig().getString("Messages.notifyAddedPlayer").replaceAll("&", "§"));

        task = BetaKey.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) BetaKey.getInstance(), new Runnable() {
            public void run() {
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                BetaKey.getInstance().getServer().getScheduler().cancelTask(task);

            }
        },0L, 100L);
        return false;
    }
}
