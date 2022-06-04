/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.bridge;

import me.filoghost.holographicdisplays.api.hologram.HologramLines;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import me.filoghost.holographicdisplays.plugin.internal.hologram.InternalHologram;
import org.bukkit.ChatColor;

public class WrappedHologram {

    private final InternalHologram hologram;

    private WrappedHologram(InternalHologram hologram) {
        this.hologram = hologram;
    }

    public static WrappedHologram wrap(InternalHologram hologram) {
        if (hologram == null) {
            return null;
        }

        return new WrappedHologram(hologram);
    }

    public String getName() {
        return hologram.getName();
    }

    public int size() {
        return hologram.getRenderedHologram().getLines().size();
    }

    public boolean isFirstLineTextLongerThan(int length) {
        HologramLines lines = hologram.getRenderedHologram().getLines();

        if (lines.size() > 0 && lines.get(0) instanceof TextHologramLine) {
            String firstLineText = ChatColor.stripColor(((TextHologramLine) lines.get(0)).getText());
            return firstLineText != null && firstLineText.length() > length;
        } else {
            return false;
        }
    }

}
