package de.coooding.betakey.commands;

import de.coooding.betakey.BetaKey;
import de.coooding.betakey.utils.UUIDFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveBetaPlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        final Player player = (Player) sender;

        if (!player.hasPermission("betakey.removeplayer") || !player.hasPermission("betakey.admin")) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.noperm").replaceAll("&", "§"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.commandUse").replaceAll("&", "§").replaceAll("%command%", "/removebetaplayer <player>"));
            return true;
        }

        String playerName = args[0];
        BetaKey.getInstance().getKeyProvider().removePlayerBeta(UUIDFetcher.getUUID(playerName));

        player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                BetaKey.getInstance().getConfig().getString("Messages.removeBetaPlayer").replaceAll("&", "§").replaceAll("%playerName%", playerName));

        return false;
    }
}
