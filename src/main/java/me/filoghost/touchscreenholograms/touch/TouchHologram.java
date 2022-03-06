/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.touch;

import me.filoghost.touchscreenholograms.utils.ConsoleLogger;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class TouchHologram {

    private static Map<Player, Long> CLICK_COOLDOWNS = new HashMap<>();

    private final String linkedHologramName;
    private final List<TouchCommand> commands;

    public TouchHologram(String linkedHologram) {
        this(linkedHologram, new ArrayList<>());
    }

    public TouchHologram(String linkedHologram, List<TouchCommand> commands) {
        this.linkedHologramName = linkedHologram;
        this.commands = commands;
    }

    public void onTouch(Player whoClicked) {
        // Avoid command spam
        Long oldCooldown = CLICK_COOLDOWNS.get(whoClicked);
        if (oldCooldown != null) {
            if (System.currentTimeMillis() < oldCooldown) {
                return;
            }
        }
        CLICK_COOLDOWNS.put(whoClicked, System.currentTimeMillis() + 500); // 0.5 seconds cooldown

        // Execute commands
        for (TouchCommand command : commands) {
            try {
                command.execute(whoClicked);
            } catch (Throwable t) {
                ConsoleLogger.log(Level.SEVERE, "A plugin has generated an exception while executing the command \"" + command.toCommandString() + "\"", t);
            }
        }
    }

    public static void removeCooldown(Player player) {
        CLICK_COOLDOWNS.remove(player);
    }

    public List<TouchCommand> getCommands() {
        return commands;
    }

    public String getLinkedHologramName() {
        return linkedHologramName;
    }

}
