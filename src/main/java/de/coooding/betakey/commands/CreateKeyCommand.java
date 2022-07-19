package de.coooding.betakey.commands;

import de.coooding.betakey.BetaKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateKeyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        final Player player = (Player) sender;

        if(!player.hasPermission("betakey.createkey") || !player.hasPermission("betakey.admin")) {
            player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                    BetaKey.getInstance().getConfig().getString("Messages.noperm").replaceAll("&", "§"));
            return true;
        }

        String betaKey = randomAlphaNumeric(BetaKey.getInstance().getConfig().getInt("Settings.keyLength"));
        BetaKey.getInstance().getKeyProvider().addBetaKey(betaKey);

        player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                BetaKey.getInstance().getConfig().getString("Messages.keyWasCreated").replaceAll("&", "§"));
        player.sendMessage(BetaKey.getInstance().getConfig().getString("Messages.prefix").replaceAll("&", "§") +
                BetaKey.getInstance().getConfig().getString("Messages.keyCreated").replaceAll("&", "§").replaceAll("%key%" , betaKey));
        return false;
    }

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random() * "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length());
            builder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(character));
        }
        return builder.toString();
    }
}
