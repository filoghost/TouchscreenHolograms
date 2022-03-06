/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.bridge;

import com.gmail.filoghost.holographicdisplays.api.line.TouchableLine;
import com.gmail.filoghost.holographicdisplays.event.HolographicDisplaysReloadEvent;
import com.gmail.filoghost.holographicdisplays.event.NamedHologramEditedEvent;
import com.gmail.filoghost.holographicdisplays.object.NamedHologram;
import com.gmail.filoghost.holographicdisplays.object.NamedHologramManager;
import com.gmail.filoghost.holographicdisplays.object.line.CraftHologramLine;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class HolographicDisplaysHelper {

    public static boolean isPluginEnabled() {
        try {
            Class.forName("com.gmail.filoghost.holographicdisplays.HolographicDisplays");
        } catch (Exception e) {
            return false;
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("HolographicDisplays");
        return plugin != null && plugin.isEnabled();
    }

    public static void registerChangeListener(Runnable listener, Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onHologramEdit(NamedHologramEditedEvent event) {
                listener.run();
            }

            @EventHandler
            public void onHolographicDisplaysReload(HolographicDisplaysReloadEvent event) {
                listener.run();
            }

        }, plugin);
    }

    public static boolean hologramExists(String hologramName) {
        return getHologram(hologramName) != null;
    }

    public static WrappedHologram getHologram(String hologramName) {
        return WrappedHologram.wrap(NamedHologramManager.getHologram(hologramName));
    }

    public static void removeTouchHandlerFromHolograms() {
        for (NamedHologram hologram : NamedHologramManager.getHolograms()) {

            for (int i = 0; i < hologram.size(); i++) {
                CraftHologramLine line = hologram.getLine(i);

                if (line instanceof TouchableLine) {
                    TouchableLine touchable = (TouchableLine) line;

                    if (touchable.getTouchHandler() != null && touchable.getTouchHandler() instanceof TouchHologram) {
                        touchable.setTouchHandler(null);
                    }
                }
            }
        }
    }

    public static void setTouchHandler(String hologramName, TouchHologram touchHologram) {
        NamedHologram hologram = NamedHologramManager.getHologram(hologramName);

        if (hologram != null && hologram.size() > 0) {
            // Unsafe cast because all lines are touchable (for now)
            ((TouchableLine) hologram.getLine(0)).setTouchHandler(touchHologram::onTouch);
        }
    }

}
