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
package me.filoghost.touchscreenholograms.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.command.sub.AddCommand;
import me.filoghost.touchscreenholograms.command.sub.ClearAllCommand;
import me.filoghost.touchscreenholograms.command.sub.DetailsCommand;
import me.filoghost.touchscreenholograms.command.sub.HelpCommand;
import me.filoghost.touchscreenholograms.command.sub.ListCommand;
import me.filoghost.touchscreenholograms.command.sub.RemoveCommand;

public class RootCommandHandler implements CommandExecutor {

	private List<SubCommand> subCommands;
	
	public RootCommandHandler() {
		subCommands = new ArrayList<>();
		
		addSubCommand(new ClearAllCommand());
		addSubCommand(new ListCommand());
		addSubCommand(new AddCommand());
		addSubCommand(new RemoveCommand());
		addSubCommand(new DetailsCommand());
		addSubCommand(new HelpCommand(this));
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("");
			sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Touchscreen Holograms");
			sender.sendMessage(ChatColor.GREEN + "Version: " + ChatColor.GRAY + TouchscreenHolograms.getInstance().getDescription().getVersion());
			sender.sendMessage(ChatColor.GREEN + "Developer: " + ChatColor.GRAY + "filoghost");
			sender.sendMessage(ChatColor.GREEN + "Commands: " + ChatColor.GRAY + "/touch help");
			return true;
		}
		
		for (SubCommand subCommand : subCommands) {
			if (isValidTrigger(subCommand, args[0])) {
				try {
					CommandValidator.isTrue(subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission()), "You don't have permission.");
					CommandValidator.isTrue(args.length - 1 >= subCommand.getMinArguments(), "Usage: /" + label + " " + subCommand.getName() + " " + subCommand.getUsage());
					
					subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
					return true;
				} catch (CommandException e) {
					sender.sendMessage(ChatColor.RED + e.getMessage());
				}
			}
		}
	
		sender.sendMessage(ChatColor.RED + "Unknown sub-command. Type \"/touch help\" for a list of commands.");
		return true;
	}
	
	
	private boolean isValidTrigger(SubCommand subCommand, String name) {
		if (subCommand.getName().equalsIgnoreCase(name)) {
			return true;
		}
		
		if (subCommand.getAliases() != null) {
			for (String alias : subCommand.getAliases()) {
				if (alias.equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	public void addSubCommand(SubCommand subCommand) {
		subCommands.add(subCommand);
	}
	
	
	public List<SubCommand> getSubCommands() {
		return Collections.unmodifiableList(subCommands);
	}

}
