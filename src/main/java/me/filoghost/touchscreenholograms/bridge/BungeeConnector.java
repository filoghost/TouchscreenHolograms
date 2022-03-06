/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.bridge;

import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeConnector {

    public static void sendToServer(Player player, String server) {
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(TouchscreenHolograms.getInstance(), "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(TouchscreenHolograms.getInstance(), "BungeeCord");
        }

        if (server.length() == 0) {
            player.sendMessage(ChatColor.RED + "Target server was \"\" (empty string) cannot connect to it.");
            return;
        }

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteArray);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException e) {
            // Should never happen
        }

        player.sendPluginMessage(TouchscreenHolograms.getInstance(), "BungeeCord", byteArray.toByteArray());
    }
}
