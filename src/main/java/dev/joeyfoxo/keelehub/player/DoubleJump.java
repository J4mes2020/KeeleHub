package dev.joeyfoxo.keelehub.player;

import com.destroystokyo.paper.ParticleBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class DoubleJump implements Listener {

    public DoubleJump() {

        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
    }

    Set<UUID> jumpers = new HashSet<>();

    @EventHandler
            (priority = EventPriority.HIGHEST)
    private void join(PlayerJoinEvent event) {
        event.getPlayer().setAllowFlight(true); // Allow flight cause we will double jump on flight attempt.

        event.joinMessage(Component.text(""));

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

    /**
     * Called when a player is trying to double jump
     *
     * @param event the event
     */
    @EventHandler
    public void attemptDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        //Don't double jump in these cases
        if (jumpers.contains(player.getUniqueId()) ||
                !event.isFlying() ||
                player.getGameMode() == GameMode.CREATIVE ||
                player.getGameMode() == GameMode.SPECTATOR)
            return;

        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);//Disable to prevent wobbling


        player.setVelocity(player.getLocation().getDirection().multiply(1).setY(0.5));
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f, -5.0f);
        jumpers.add(player.getUniqueId());
        new ParticleBuilder(Particle.SMOKE_NORMAL).count(5).location(player.getLocation()).receivers(player);
    }

    /**
     * Block fall damage of double jump
     *
     * @param event the event
     */
    @EventHandler
    public void damageFall(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        if (!jumpers.contains(event.getEntity().getUniqueId()) || event.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;
        event.setCancelled(true);
    }

    /**
     * Called when player switches his game mode
     *
     * @param event the event
     */
    @EventHandler
    public void refresh(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!jumpers.contains(player.getUniqueId()))
            return;

        Location belowPlayer = player.getLocation().subtract(0, 0.1, 0);
        Block block = belowPlayer.getBlock();

        // Player definitely not grounded so no refresh in sight
        if (block.isEmpty() || block.isLiquid())
            return;

        // No normal block below
        if (isNonGroundMaterial(block.getType()))
            return;
        player.setAllowFlight(true);
        jumpers.remove(player.getUniqueId());
    }

    /**
     * Called when player switches his game mode
     *
     * @param event the event
     */
    @EventHandler
    public void switchGameMode(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("doublejump.jump") ||
                event.getNewGameMode() == GameMode.CREATIVE ||
                event.getNewGameMode() == GameMode.SPECTATOR)
            return;
        player.setAllowFlight(true);
        player.setFlying(false);
    }

    /**
     * Checks a material if it is a block the players double jump should not be refreshed on
     *
     * @param type Material to check
     * @return is non ground material
     */
    private boolean isNonGroundMaterial(Material type) {
        return type == Material.LADDER ||
                type == Material.VINE ||
                type == Material.GRASS ||
                type.toString().contains("WALL") ||
                type.toString().contains("FENCE") || // Filters out all fences and gates
                type.toString().contains("DOOR"); // Filters out doors and trapdoors
    }


}
