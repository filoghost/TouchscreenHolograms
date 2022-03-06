/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.command.sub;

import me.filoghost.touchscreenholograms.Perms;
import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.command.CommandException;
import me.filoghost.touchscreenholograms.command.CommandValidator;
import me.filoghost.touchscreenholograms.command.SubCommand;
import me.filoghost.touchscreenholograms.disk.TouchHologramStorage;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import me.filoghost.touchscreenholograms.touch.TouchManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ClearAllCommand extends SubCommand {

    public ClearAllCommand() {
        super("clearAll", "removeAll");
        setPermission(Perms.MAIN_PERMISSION);
        setMinArguments(1);
        setUsage("<hologram>");
        setDescription("Clears all the commands from a hologram.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        TouchManager touchManager = TouchscreenHolograms.getTouchManager();
        TouchHologramStorage fileStorage = TouchscreenHolograms.getFileStorage();

        TouchHologram hologram = touchManager.getByName(args[0]);
        CommandValidator.notNull(hologram, "There are no associated commands to this hologram. The name is case sensitive.");

        touchManager.remove(hologram);
        touchManager.refreshHolograms();
        fileStorage.deleteTouchHologram(hologram);

        sender.sendMessage(ChatColor.GREEN + "You removed all the commands from this hologram.");
        fileStorage.trySaveToDisk(sender);
    }

}
