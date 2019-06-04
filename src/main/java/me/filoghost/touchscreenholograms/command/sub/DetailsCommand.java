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
package me.filoghost.touchscreenholograms.command.sub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.filoghost.touchscreenholograms.Perms;
import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.command.CommandException;
import me.filoghost.touchscreenholograms.command.CommandValidator;
import me.filoghost.touchscreenholograms.command.SubCommand;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import me.filoghost.touchscreenholograms.touch.TouchManager;

public class DetailsCommand extends SubCommand {
	
	public DetailsCommand() {
		super("details");
		setPermission(Perms.MAIN_PERMISSION);
		setMinArguments(1);
		setUsage("<hologram>");
		setDescription("Lists all the commands in a hologram.");
	}


	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		TouchManager touchManager = TouchscreenHolograms.getTouchManager();
		
		TouchHologram hologram = touchManager.getByName(args[0]);
		CommandValidator.isTrue(hologram != null && hologram.getCommands().size() > 0, "There are no commands associated with this hologram. The name is case sensitive.");
		
		sender.sendMessage("");
		sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + hologram.getLinkedHologramName() + "'s commands (" + hologram.getCommands().size() + "):");
		for (int i = 0; i < hologram.getCommands().size(); i++) {
			sender.sendMessage(ChatColor.GREEN + "" + (i+1) + ") " + ChatColor.GRAY + hologram.getCommands().get(i).toCommandString());
		}
		
	}

}
