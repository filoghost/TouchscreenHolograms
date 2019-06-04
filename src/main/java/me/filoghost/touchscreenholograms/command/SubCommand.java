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

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
	
	private final String name;
	private final String[] aliases;
	
	private String permission;
	private String usage;
	private int minArguments;
	private String description;
	

	public abstract void execute(CommandSender sender, String[] args) throws CommandException;
	
	
	public SubCommand(String name) {
		this(name, new String[0]);
	}
	
	public SubCommand(String name, String... aliases) {
		this.name = name;
		this.aliases = aliases;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public void setMinArguments(int minArguments) {
		this.minArguments = minArguments;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String[] getAliases() {
		return aliases;
	}

	public String getPermission() {
		return permission;
	}

	public String getUsage() {
		return usage;
	}

	public int getMinArguments() {
		return minArguments;
	}

	public String getDescription() {
		return description;
	}

}
