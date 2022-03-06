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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;

import me.filoghost.touchscreenholograms.utils.ConsoleLogger;

public class TouchHologram implements TouchHandler {

    private static Map<Player, Long> CLICK_COOLDOWNS = new HashMap<>();

    private final String linkedHologramName;
    private final List<TouchCommand> commands;

    public TouchHologram(String linkedHologram) {
        this(linkedHologram, new ArrayList<>());
    }

    public TouchHologram(String linkedHologram, List<TouchCommand> commands) {
        this.linkedHologramName = linkedHologram;
        this.commands = commands;
    }

    @Override
    public void onTouch(Player whoClicked) {
        // Avoid command spam
        Long oldCooldown = CLICK_COOLDOWNS.get(whoClicked);
        if (oldCooldown != null) {
            if (System.currentTimeMillis() < oldCooldown) {
                return;
            }
        }
        CLICK_COOLDOWNS.put(whoClicked, System.currentTimeMillis() + 500); // 0.5 seconds cooldown

        // Execute commands
        for (TouchCommand command : commands) {
            try {
                command.execute(whoClicked);
            } catch (Throwable t) {
                ConsoleLogger.log(Level.SEVERE, "A plugin has generated an exception while executing the command \"" + command.toCommandString() + "\"", t);
            }
        }
    }

    public static void removeCooldown(Player player) {
        CLICK_COOLDOWNS.remove(player);
    }

    public List<TouchCommand> getCommands() {
        return commands;
    }

    public String getLinkedHologramName() {
        return linkedHologramName;
    }

}
