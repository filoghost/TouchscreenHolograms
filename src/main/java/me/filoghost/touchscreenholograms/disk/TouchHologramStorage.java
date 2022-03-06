/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.disk;

import me.filoghost.touchscreenholograms.touch.TouchCommand;
import me.filoghost.touchscreenholograms.touch.TouchHologram;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TouchHologramStorage extends ConfigBase {

    public TouchHologramStorage(File file) {
        super(file);
    }

    public TouchHologram loadTouchHologram(String name) {
        List<String> commandsStrings = config.getStringList(name + ".commands");
        List<TouchCommand> commands = new ArrayList<>();

        for (String commandString : commandsStrings) {
            commands.add(TouchCommand.fromCommandString(commandString));
        }

        return new TouchHologram(name, commands);
    }

    public void deleteTouchHologram(TouchHologram hologram) {
        config.set(hologram.getLinkedHologramName(), null);
    }

    public void deleteTouchHologram(String hologramName) {
        config.set(hologramName, null);
    }

    public void saveTouchHologram(TouchHologram hologram) {
        List<String> tempList = new ArrayList<>();

        for (TouchCommand command : hologram.getCommands()) {
            tempList.add(command.toCommandString());
        }

        config.set(hologram.getLinkedHologramName() + ".commands", tempList);
    }

    public Set<String> getTouchHolograms() {
        return config.getKeys(false);
    }

}
