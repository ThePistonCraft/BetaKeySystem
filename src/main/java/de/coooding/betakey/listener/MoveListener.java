package de.coooding.betakey.listener;

import de.coooding.betakey.BetaKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if(BetaKey.getInstance().getKeyProvider().isBeta()) {
            if(!BetaKey.getInstance().getKeyProvider().isPlayerInBeta(player.getUniqueId())) {
                player.teleport(player);
            }
            event.setCancelled(false);
        }
    }
}
