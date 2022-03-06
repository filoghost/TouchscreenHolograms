/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Format {

    public static void sendWarning(CommandSender recipient, String warning) {
        recipient.sendMessage(ChatColor.RED + "( " + ChatColor.DARK_RED + ChatColor.BOLD + "!" + ChatColor.RESET + ChatColor.RED + " ) " + ChatColor.GRAY + warning);
    }

    public static void sendTip(CommandSender recipient, String tip) {
        recipient.sendMessage(ChatColor.WHITE + "[TIP] " + ChatColor.GRAY + tip);
    }

}
