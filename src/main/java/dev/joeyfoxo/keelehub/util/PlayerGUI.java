package dev.joeyfoxo.keelehub.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public interface PlayerGUI {

    void createGUI(InventoryHolder owner, int size, Component title);

    void closeGUI(Player player);

    void openGUI(Player player);

    ItemStack createItem(Material material, Component component);
    ItemStack createItem(Material material, Component component, int ItemID);

    ItemStack createItem(Material material, Component component, int ItemID, List<Component> lore);

}
