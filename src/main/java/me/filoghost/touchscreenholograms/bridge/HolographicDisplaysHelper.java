/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.bridge;

import me.filoghost.holographicdisplays.api.hologram.HologramLines;
import me.filoghost.holographicdisplays.api.hologram.line.ClickableHologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.HologramLineClickEvent;
import me.filoghost.holographicdisplays.api.hologram.line.HologramLineClickListener;
import me.filoghost.holographicdisplays.plugin.HolographicDisplays;
import me.filoghost.holographicdisplays.plugin.event.HolographicDisplaysReloadEvent;
import me.filoghost.holographicdisplays.plugin.event.InternalHologramChangeEvent;
import me.filoghost.holographicdisplays.plugin.internal.hologram.InternalHologram;
import me.filoghost.holographicdisplays.plugin.internal.hologram.InternalHologramManager;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class HolographicDisplaysHelper {

    private static InternalHologramManager hologramManager;

    public static boolean init() {
        try {
            Class.forName("me.filoghost.holographicdisplays.api.HolographicDisplaysAPI");
        } catch (Exception e) {
            return false;
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("HolographicDisplays");
        if (plugin == null || !plugin.isEnabled()) {
            return false;
        }

        hologramManager = HolographicDisplays.getInstance().getInternalHologramManager();
        return true;
    }

    public static void registerChangeListener(Runnable listener, Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onHologramEdit(InternalHologramChangeEvent event) {
                listener.run();
            }

            @EventHandler
            public void onHolographicDisplaysReload(HolographicDisplaysReloadEvent event) {
                listener.run();
            }

        }, plugin);
    }

    public static boolean hologramExists(String hologramName) {
        return hologramManager.getHologramByName(hologramName) != null;
    }

    public static WrappedHologram getHologram(String hologramName) {
        return WrappedHologram.wrap(hologramManager.getHologramByName(hologramName));
    }

    public static void removeTouchHandlerFromHolograms() {
        for (InternalHologram hologram : hologramManager.getHolograms()) {
            HologramLines lines = hologram.getRenderedHologram().getLines();

            for (int i = 0; i < lines.size(); i++) {
                // Unsafe cast: all lines are touchable
                ClickableHologramLine line = (ClickableHologramLine) lines.get(i);
                if (line.getClickListener() instanceof TouchListenerAdapter) {
                    line.setClickListener(null);
                }
            }
        }
    }

    public static void setTouchHandler(String hologramName, TouchHologram touchHologram) {
        InternalHologram hologram = hologramManager.getHologramByName(hologramName);

        if (hologram != null) {
            HologramLines lines = hologram.getRenderedHologram().getLines();

            if (lines.size() > 0) {
                // Unsafe cast: all lines are touchable
                System.out.println("Setting listener on " + hologram.getName());
                ((ClickableHologramLine) lines.get(0)).setClickListener(new TouchListenerAdapter(touchHologram));
            }
        }
    }


    private static class TouchListenerAdapter implements HologramLineClickListener {

        private final TouchHologram touchHologram;

        TouchListenerAdapter(TouchHologram touchHologram) {
            this.touchHologram = touchHologram;
        }

        @Override
        public void onClick(HologramLineClickEvent event) {
            touchHologram.onTouch(event.getPlayer());
        }

    }

}
