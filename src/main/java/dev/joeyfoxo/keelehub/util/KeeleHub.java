package dev.joeyfoxo.keelehub.util;

import dev.joeyfoxo.keelehub.ListenerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleHub extends JavaPlugin {

    public static KeeleHub keeleHub;

    @Override
    public void onEnable() {
        keeleHub = this;
        new ListenerManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
