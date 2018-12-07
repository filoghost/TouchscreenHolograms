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
package com.gmail.filoghost.touchscreen.command.sub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.filoghost.touchscreen.Perms;
import com.gmail.filoghost.touchscreen.TouchManager;
import com.gmail.filoghost.touchscreen.TouchscreenHolograms;
import com.gmail.filoghost.touchscreen.command.CommandException;
import com.gmail.filoghost.touchscreen.command.CommandValidator;
import com.gmail.filoghost.touchscreen.command.SubCommand;
import com.gmail.filoghost.touchscreen.disk.TouchHologramStorage;
import com.gmail.filoghost.touchscreen.touch.TouchHologram;

public class ClearAllCommand extends SubCommand {

	public ClearAllCommand() {
		super("clearAll", "removeAll");
		setPermission(Perms.MAIN_PERMISSION);
		setMinArguments(1);
		setUsage("<hologram>");
		setDescription("Clears all the commands from a hologram.");
	}

	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		TouchManager touchManager = TouchscreenHolograms.getTouchManager();
		TouchHologramStorage fileStorage = TouchscreenHolograms.getFileStorage();
		
		TouchHologram hologram = touchManager.getByName(args[0]);
		CommandValidator.notNull(hologram, "There are no associated commands to this hologram. The name is case sensitive.");
		
		touchManager.remove(hologram);
		touchManager.refreshHolograms();
		fileStorage.deleteTouchHologram(hologram);
		
		sender.sendMessage(ChatColor.GREEN + "You removed all the commands from this hologram.");
		fileStorage.trySaveToDisk(sender);
	}

}
