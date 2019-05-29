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
import com.gmail.filoghost.touchscreen.command.CommandException;
import com.gmail.filoghost.touchscreen.command.RootCommandHandler;
import com.gmail.filoghost.touchscreen.command.SubCommand;
import com.gmail.filoghost.touchscreen.utils.Format;

public class HelpCommand extends SubCommand {
	
	private RootCommandHandler commandHandler;

	public HelpCommand(RootCommandHandler commandHandler) {
		super("help");
		this.commandHandler = commandHandler;
		setPermission(Perms.MAIN_PERMISSION);
		setMinArguments(0);
		setUsage("[command]");
		setDescription("Lists all the possible commands.");
		
	}


	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			for (SubCommand subCommand : commandHandler.getSubCommands()) {
				if (subCommand.getName().equalsIgnoreCase(args[0])) {
					sender.sendMessage("");
					sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Help about the command \"/touch " + subCommand.getName() + "\"");
					sender.sendMessage(ChatColor.GRAY + subCommand.getDescription());
					return;
				}
			}
			
			sender.sendMessage(ChatColor.RED + "Unknown sub-command. Type \"/touch help\" for a list of commands.");

		} else {
			sender.sendMessage("");
			sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Touchscreen Holograms commands");
			for (SubCommand subCommand : commandHandler.getSubCommands()) {
				if (!(subCommand instanceof HelpCommand)) {
					String usage = "/touch " + subCommand.getName() + (subCommand.getUsage().length() > 0 ? " " + subCommand.getUsage() : "");
					sender.sendMessage(ChatColor.GREEN + usage);
				}
			}
		
			sender.sendMessage("");
			Format.sendTip(sender, "See help about a command with /touch help [command]");
		}
	}

}
