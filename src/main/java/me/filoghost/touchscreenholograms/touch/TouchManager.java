/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.touch;

import me.filoghost.touchscreenholograms.bridge.HolographicDisplaysHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TouchManager {

    private final Map<String, TouchHologram> touchHologramsByName = new LinkedHashMap<>();

    public void add(TouchHologram touchHologram) {
        touchHologramsByName.put(touchHologram.getLinkedHologramName().toLowerCase(), touchHologram);
    }

    public void remove(TouchHologram touchHologram) {
        touchHologramsByName.remove(touchHologram.getLinkedHologramName().toLowerCase());
    }

    public TouchHologram getByName(String name) {
        return touchHologramsByName.get(name.toLowerCase());
    }

    public List<TouchHologram> getTouchHolograms() {
        return new ArrayList<>(touchHologramsByName.values());
    }

    public void refreshHolograms() {
        // Remove all the touch handlers that belong to this plugin
        HolographicDisplaysHelper.removeTouchHandlerFromHolograms();

        // Add the touch handlers back
        for (TouchHologram touch : touchHologramsByName.values()) {
            HolographicDisplaysHelper.setTouchHandler(touch.getLinkedHologramName(), touch);
        }
    }

}
