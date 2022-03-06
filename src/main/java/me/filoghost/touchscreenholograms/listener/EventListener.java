/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.listener;

import me.filoghost.touchscreenholograms.Perms;
import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (TouchscreenHolograms.getNewVersion() != null && event.getPlayer().hasPermission(Perms.MAIN_PERMISSION) && TouchscreenHolograms.getSettings().updateNotification) {
            event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "[Touchscreen Holograms] " + ChatColor.GREEN + "Found an update: " + TouchscreenHolograms.getNewVersion() + ". Download:");
            event.getPlayer().sendMessage(ChatColor.DARK_GREEN + ">> " + ChatColor.GREEN + "https://dev.bukkit.org/projects/touchscreen-holograms");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        TouchHologram.removeCooldown(event.getPlayer());
    }

}
