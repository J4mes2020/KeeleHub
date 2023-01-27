package dev.joeyfoxo.keelehub.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class Interactions implements Listener {

    public Interactions() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void weatherControl(WeatherChangeEvent event) {

        if (event.toWeatherState()) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void thunderControl(ThunderChangeEvent event) {

        if (event.toThunderState()) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void timeControl(BlockCanBuildEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setBuildable(false);
        }
    }

    @EventHandler
    public void entitySpawning(CreatureSpawnEvent event) {

        event.setCancelled(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND
                && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDrop(PlayerAttemptPickupItemEvent event) {

        if (!event.getPlayer().hasPermission("kh.admin")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getClickedBlock() != null && event.getAction().isRightClick()) {
            if (event.getClickedBlock().getType().toString().contains("DOOR")
                    || event.getClickedBlock().getType().toString().contains("GATE")
                    || event.getClickedBlock().getType().toString().contains("BUTTON")
                    || event.getClickedBlock().getType().toString().contains("FRAME")
                    || event.getClickedBlock().getType().toString().contains("CHEST")) {

                event.setCancelled(true);

            }
        }

    }

}
