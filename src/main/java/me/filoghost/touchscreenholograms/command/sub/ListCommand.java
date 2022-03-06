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
import me.filoghost.touchscreenholograms.utils.Format;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListCommand extends SubCommand {

    private static final int HOLOGRAMS_PER_PAGE = 10;

    public ListCommand() {
        super("list");
        setPermission(Perms.MAIN_PERMISSION);
        setMinArguments(0);
        setUsage("[page]");
        setDescription("Lists all the touch holograms.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        TouchManager touchManager = TouchscreenHolograms.getTouchManager();

        int page = args.length > 0 ? CommandValidator.getInteger(args[0]) : 1;
        CommandValidator.isTrue(page >= 1, "Page number must be 1 or greater.");

        List<TouchHologram> touchHolograms = touchManager.getTouchHolograms();
        CommandValidator.isTrue(touchHolograms.size() > 0, "There are no touch holograms.");

        int totalPages = touchHolograms.size() / HOLOGRAMS_PER_PAGE;
        if (touchHolograms.size() % HOLOGRAMS_PER_PAGE != 0) {
            totalPages++;
        }

        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Touch Holograms list " + ChatColor.WHITE + "(Page " + page + " of " + totalPages + ")");
        int fromIndex = (page - 1) * HOLOGRAMS_PER_PAGE;
        int toIndex = fromIndex + HOLOGRAMS_PER_PAGE;

        for (int i = fromIndex; i < toIndex; i++) {
            if (i < touchHolograms.size()) {
                TouchHologram hologram = touchHolograms.get(i);
                sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.GREEN + "'" + hologram.getLinkedHologramName() + "' " + ChatColor.GRAY + "with " + hologram.getCommands().size() + " command(s)");
            }
        }

        sender.sendMessage("");
        Format.sendTip(sender, "See the commands with /touch details <hologram>");

        if (page < totalPages) {
            Format.sendTip(sender, "See the next page with /touch list " + (page + 1));
        }
    }

}
