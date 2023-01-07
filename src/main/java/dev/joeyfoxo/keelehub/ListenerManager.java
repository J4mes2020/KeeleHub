package dev.joeyfoxo.keelehub;

import dev.joeyfoxo.keelehub.hubselector.HubSelectorGUI;
import dev.joeyfoxo.keelehub.player.DoubleJump;
import dev.joeyfoxo.keelehub.player.HungerCheck;
import dev.joeyfoxo.keelehub.player.Interactions;
import dev.joeyfoxo.keelehub.player.JoinAndLeaveEvents;

public class ListenerManager {

    public ListenerManager() {

        new JoinAndLeaveEvents();
        new HubSelectorGUI();
        new DoubleJump();
        new HungerCheck();
        new Interactions();

    }
}
