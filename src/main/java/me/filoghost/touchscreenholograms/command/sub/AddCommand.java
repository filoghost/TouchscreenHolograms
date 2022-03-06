/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.command.sub;

import me.filoghost.touchscreenholograms.Perms;
import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.bridge.HolographicDisplaysHelper;
import me.filoghost.touchscreenholograms.bridge.WrappedHologram;
import me.filoghost.touchscreenholograms.command.CommandException;
import me.filoghost.touchscreenholograms.command.CommandValidator;
import me.filoghost.touchscreenholograms.command.SubCommand;
import me.filoghost.touchscreenholograms.disk.TouchHologramStorage;
import me.filoghost.touchscreenholograms.touch.TouchCommand;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import me.filoghost.touchscreenholograms.touch.TouchManager;
import me.filoghost.touchscreenholograms.utils.Format;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AddCommand extends SubCommand {

    public AddCommand() {
        super("add", "addcmd", "addcommand");
        setPermission(Perms.MAIN_PERMISSION);
        setMinArguments(2);
        setUsage("<hologram> <command>");
        setDescription("Adds a command to a hologram.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        TouchManager touchManager = TouchscreenHolograms.getTouchManager();
        TouchHologramStorage fileStorage = TouchscreenHolograms.getFileStorage();

        TouchHologram touch = touchManager.getByName(args[0]);

        if (touch == null) {
            WrappedHologram hologram = HolographicDisplaysHelper.getHologram(args[0]);
            CommandValidator.notNull(hologram, "There's no hologram with that name. The name is case sensitive.");

            touch = new TouchHologram(hologram.getName());
            touchManager.add(touch);
            touchManager.refreshHolograms();

            if (hologram.size() == 0) {
                Format.sendWarning(sender, "The hologram has no lines!");
            } else {
                if (hologram.size() > 1) {
                    Format.sendWarning(sender, "You selected a hologram that contains more than one line: it's recommanded to use holograms with a single line, because the click wouldn't be detected correctly in the other lines.");
                }

                // If the text is too big for the touch hitbox, send a warning message
                if (hologram.isFirstLineTextLongerThan(6)) {
                    Format.sendWarning(sender, "You selected a hologram with the first line longer than 6 chars. Since the holograms rotate (client side), you should reduce the length, because the click wouldn't be detected correctly.");
                }
            }
        }

        String command = StringUtils.join(args, " ", 1, args.length);
        if (command.startsWith("/")) {
            Format.sendWarning(sender,"You used a slash before the command, is that an error? For normal commands, do not include the slash.");
        }

        touch.getCommands().add(TouchCommand.fromCommandString(command));

        fileStorage.saveTouchHologram(touch);

        sender.sendMessage(ChatColor.GREEN + "Command added to the hologram!");
        fileStorage.trySaveToDisk(sender);
    }

}
