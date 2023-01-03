package dev.joeyfoxo.keelehub;

import dev.joeyfoxo.keelehub.player.DoubleJump;
import dev.joeyfoxo.keelehub.player.HungerCheck;
import dev.joeyfoxo.keelehub.player.Interactions;

public class ListenerManager {

    public ListenerManager() {

        new DoubleJump();
        new HungerCheck();
        new Interactions();

    }
}
