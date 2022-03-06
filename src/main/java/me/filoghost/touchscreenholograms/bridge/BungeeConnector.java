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
package me.filoghost.touchscreenholograms.bridge;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.filoghost.touchscreenholograms.TouchscreenHolograms;

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
