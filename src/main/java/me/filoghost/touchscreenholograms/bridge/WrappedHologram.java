/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.bridge;

import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.gmail.filoghost.holographicdisplays.object.NamedHologram;
import org.bukkit.ChatColor;

public class WrappedHologram {

    private final NamedHologram hologram;

    private WrappedHologram(NamedHologram hologram) {
        this.hologram = hologram;
    }

    public static WrappedHologram wrap(NamedHologram hologram) {
        if (hologram == null) {
            return null;
        }

        return new WrappedHologram(hologram);
    }

    public String getName() {
        return hologram.getName();
    }

    public int size() {
        return hologram.size();
    }

    public boolean isFirstLineTextLongerThan(int length) {
        if (size() > 0 && hologram.getLine(0) instanceof TextLine) {
            String firstLineText = ChatColor.stripColor(((TextLine) hologram.getLine(0)).getText());
            return firstLineText.length() > length;
        } else {
            return false;
        }
    }

}
