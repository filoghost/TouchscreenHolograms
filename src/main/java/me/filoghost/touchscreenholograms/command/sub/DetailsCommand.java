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
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import me.filoghost.touchscreenholograms.touch.TouchManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DetailsCommand extends SubCommand {

    public DetailsCommand() {
        super("details");
        setPermission(Perms.MAIN_PERMISSION);
        setMinArguments(1);
        setUsage("<hologram>");
        setDescription("Lists all the commands in a hologram.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        TouchManager touchManager = TouchscreenHolograms.getTouchManager();

        TouchHologram hologram = touchManager.getByName(args[0]);
        CommandValidator.isTrue(hologram != null && hologram.getCommands().size() > 0, "There are no commands associated with this hologram. The name is case sensitive.");

        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + hologram.getLinkedHologramName() + "'s commands (" + hologram.getCommands().size() + "):");
        for (int i = 0; i < hologram.getCommands().size(); i++) {
            sender.sendMessage(ChatColor.GREEN + "" + (i+1) + ") " + ChatColor.GRAY + hologram.getCommands().get(i).toCommandString());
        }
    }

}
