package dev.joeyfoxo.keelehub.hubselector;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.joeyfoxo.keelehub.util.PlayerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dev.joeyfoxo.keelehub.KeeleHub.keeleHub;

public class HubSelectorGUI implements PlayerGUI, Listener{

    Inventory GUI;
    ByteArrayDataOutput output;

    ItemStack selector;

    public HubSelectorGUI(ItemStack selector) {
        createGUI(null, 9, Component.text("Select Gamemode").color(TextColor.color(168, 0, 82)));
        this.selector = selector;
        keeleHub.getServer().getPluginManager().registerEvents(this, keeleHub);
        keeleHub.getServer().getMessenger().registerOutgoingPluginChannel(keeleHub, "BungeeCord");
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if ((event.getAction().isRightClick()
                || event.getAction().isLeftClick())
                && (player.getInventory().getItemInMainHand().isSimilar(selector)
                || player.getInventory().getItemInOffHand().isSimilar(selector))) {

            openGUI(player);
        }

    }

    private void createSurvivalItem() {
        List<Component> lore = new ArrayList<>();

        lore.add(Component.text()
                .content("Version 1.8-1.19.3")
                .color(TextColor.color(84, 84, 84))
                .decoration(TextDecoration.ITALIC, false)
                .build());

        lore.add(Component.text().content("").build());

        lore.add(Component.text()
                .content("A special twist on survival with custom")
                .color(TextColor.color(134, 134, 134))
                .decoration(TextDecoration.ITALIC, false)
                .build());

        lore.add(Component.text()
                .content("terrain, unclaimed bases can be raided,")
                .color(TextColor.color(134, 134, 134))
                .decoration(TextDecoration.ITALIC, false)
                .build());

        lore.add(Component.text()
                .content("bounties and so much more!")
                .color(TextColor.color(134, 134, 134))
                .decoration(TextDecoration.ITALIC, false)
                .build());

        GUI.setItem(2, createItem(Material.GRASS_BLOCK, Component.text()
                .content("Survival")
                .color(TextColor.color(62, 237, 61))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build(), 0, lore));
    }

    public void addItemsToGUI() {

        createSurvivalItem();


        GUI.setItem(4, createItem(Material.OAK_DOOR, Component.text()
                .content("Towny")
                .color(TextColor.color(255, 221, 0))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build(), 1));

        GUI.setItem(6, createItem(Material.RED_CONCRETE_POWDER, Component.text()
                .content("University Wars")
                .color(TextColor.color(255, 81, 79))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build(), 2));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (player.getGameMode() == GameMode.CREATIVE) {
            if (event.getInventory().getType() == InventoryType.CREATIVE || event.getClickedInventory() == player.getInventory()) {
                event.setCancelled(false);
            }
        }

        if (event.getClickedInventory() == GUI) {

            if (event.getCurrentItem() != null) {

                switch (event.getCurrentItem().getItemMeta().getCustomModelData()) {

                    case 0 -> {

                        output = ByteStreams.newDataOutput();
                        output.writeUTF("Connect");
                        output.writeUTF("survival");
                        player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
                    }
                    case 1 -> {
                        output = ByteStreams.newDataOutput();
                        output.writeUTF("Connect");
                        output.writeUTF("advancedsurvival");
                        player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
                    }
                    case 2 -> {
                        output = ByteStreams.newDataOutput();
                        output.writeUTF("Connect");
                        output.writeUTF("kitpvp");
                        player.sendPluginMessage(keeleHub, "BungeeCord", output.toByteArray());
                    }

                }
                event.setCancelled(true);
            }

        }

    }

    @Override
    public void createGUI(InventoryHolder owner, int size, Component title) {
        GUI = Bukkit.createInventory(owner, size, title);
        addItemsToGUI();
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
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(component);
        item.setItemMeta(meta);
        return item;
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

    @Override
    public ItemStack createItem(Material material, Component component, int itemID, List<Component> lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.lore(lore);
        meta.setCustomModelData(itemID);
        meta.displayName(component);
        item.setItemMeta(meta);
        return item;

    }
}
