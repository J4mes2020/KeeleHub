package dev.joeyfoxo.keelehub;

import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleHub extends JavaPlugin {

    public static KeeleHub keeleHub;

    @Override
    public void onEnable() {
        keeleHub = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
