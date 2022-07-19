package de.coooding.betakey.commands;

import de.coooding.betakey.BetaKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveKeyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        final Player player = (Player) sender;

        if(!player.hasPermission("betakey.createkey") || !player.hasPermission("betakey.admin")) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.noperm").replaceAll("&", "§").replaceAll("&", "§"));
            return true;
        }

        if(args.length != 1) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.commandUse").replaceAll("&", "§").replaceAll("%command%", "/removekey <betaKey>"));
            return true;
        }

        String betaKey = args[0];
        BetaKey.getInstance().getKeyProvider().removeBetaKey(betaKey);

        player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                BetaKey.getInstance().getConfig().getString("Messages.removeBetaPlayer").replaceAll("%command%", "/removekey <betaKey>"));


        return false;
    }
}
