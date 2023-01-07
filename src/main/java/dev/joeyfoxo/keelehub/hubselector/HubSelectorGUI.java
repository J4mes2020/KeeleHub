package dev.joeyfoxo.keelehub.hubselector;

import dev.joeyfoxo.keelehub.util.PlayerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static dev.joeyfoxo.keelehub.util.KeeleHub.keeleHub;

public class HubSelectorGUI implements PlayerGUI, Listener {

    Inventory GUI;

    public HubSelectorGUI() {
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
        createGUI(null, 9, Component.text("Select Gamemode").color(TextColor.color(168, 0, 82)));
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack selector = new HubSelectorItem().getHubSelector();

        if ((event.getAction().isRightClick()
                || event.getAction().isLeftClick())
                && (player.getInventory().getItemInMainHand().isSimilar(selector)
                || player.getInventory().getItemInOffHand().isSimilar(selector))) {

            openGUI(player);
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        if (event.getClickedInventory() == GUI) {

            if (event.getCurrentItem() != null) {

                switch (event.getCurrentItem().getItemMeta().getCustomModelData()) {

                    case 0 -> player.performCommand("server survival");
                    case 1 -> player.performCommand("server advancedsurvival");
                    case 2 -> player.performCommand("server kitpvp");

                }

            }

        }

    }

    @Override
    public void createGUI(InventoryHolder owner, int size, Component title) {
        GUI = Bukkit.createInventory(owner, size, title);
        GUI.setItem(2, createItem(Material.GRASS_BLOCK, Component.text()
                .content("Survival")
                .color(TextColor.color(82, 122, 45))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build(), 0));

        GUI.setItem(4, createItem(Material.CHORUS_PLANT, Component.text()
                .content("Advanced Survival")
                .color(TextColor.color(255, 221, 0))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build(), 1));

        GUI.setItem(6, createItem(Material.BARRIER, Component.text()
                .content("KitPvP")
                .color(TextColor.color(195, 0, 0))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build(), 2));

    }

    @Override
    public void closeGUI(Player player) {

        player.closeInventory();

    }

    @Override
    public void openGUI(Player player) {
        player.openInventory(GUI);
    }

    @Override
    public ItemStack createItem(Material material, Component component) {
        return null;
    }

    @Override
    public ItemStack createItem(Material material, Component component, int itemID) {

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(itemID);
        meta.displayName(component);
        item.setItemMeta(meta);
        return item;

    }
}
