/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.filoghost.touchscreenholograms.touch;

import me.filoghost.touchscreenholograms.bridge.HolographicDisplaysHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TouchManager {

    private Map<String, TouchHologram> touchHologramsByName = new LinkedHashMap<>();

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
