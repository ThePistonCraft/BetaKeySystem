package de.coooding.betakey.commands;

import de.coooding.betakey.BetaKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleBetaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        final Player player = (Player) sender;

        if (!player.hasPermission("betakey.toggelbeta") || !player.hasPermission("betakey.admin")) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.noperm").replaceAll("&", "§"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.commandUse").replaceAll("&", "§").replaceAll("%command%", "/toggelbeta <on/off>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            BetaKey.getInstance().getKeyProvider().setBeta(true);
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.setBetaOn").replaceAll("&", "§"));
            return true;
        }
        if (args[0].equalsIgnoreCase("off")) {
            BetaKey.getInstance().getKeyProvider().setBeta(false);
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.setBetaOff").replaceAll("&", "§"));
            return true;
        } else {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.commandUse").replaceAll("&", "§").replaceAll("%command%", "/toggelbeta <on/off>"));
        }

        return false;
    }
}
