package dev.joeyfoxo.keelehub.player;

import dev.joeyfoxo.keelehub.hubselector.HubSelectorItem;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static dev.joeyfoxo.keelehub.util.KeeleHub.keeleHub;

public class JoinAndLeaveEvents implements Listener {

    public JoinAndLeaveEvents() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
    }

    @EventHandler
            (priority = EventPriority.HIGHEST)
    private void join(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setAllowFlight(true); // Allow flight cause we will double jump on flight attempt.
        event.joinMessage(Component.text(""));

        player.getInventory().setItem(4, new HubSelectorItem().getHubSelector());

    }

    @EventHandler
            (priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent event) {
        leave(event.getPlayer());
        event.quitMessage(Component.text(""));
    }

    @EventHandler
    public void quit(PlayerKickEvent event) {
        leave(event.getPlayer());
    }

    private void leave(Player player) {
        if (player == null)
            return;

        if (player.getGameMode() != GameMode.CREATIVE) //This line might cause issue's with other plugins
            player.setAllowFlight(false);
    }
}
