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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.filoghost.touchscreenholograms.bridge.BungeeConnector;

public class TouchCommand {

	private String command;
	private TouchCommandType type;
	
	private TouchCommand(String command, TouchCommandType type) {
		this.command = command;
		this.type = type;
	}
	
	public String toCommandString() {
		if (type != TouchCommandType.DEFAULT) {
			return type.getPrefix() + " " + command;
		} else {
			return command;
		}
	}

	public static TouchCommand fromCommandString(String commandString) {
		if (commandString == null) {
			commandString = "";
		}
		
		commandString = commandString.trim();
		TouchCommandType type = TouchCommandType.DEFAULT;
		
		for (TouchCommandType possibleType : TouchCommandType.values()) {
			if (possibleType != TouchCommandType.DEFAULT) {
				if (commandString.toLowerCase().startsWith(possibleType.getPrefix())) {
					type = possibleType;
					commandString = commandString.substring(possibleType.getPrefix().length());
				}
			}
		}

		commandString = commandString.trim();
		commandString = ChatColor.translateAlternateColorCodes('&', commandString);
		return new TouchCommand(commandString, type);
	}
	

	public void execute(Player player) throws Exception {
		if (command == null || command.length() == 0) {
			return;
		}
		
		// With placeholders
		String localCommand = command.replace("{player}", player.getName())
									 .replace("{world}", player.getWorld().getName())
									 .replace("{online}", Integer.toString(Bukkit.getOnlinePlayers().size()))
									 .replace("{max_players}", Integer.toString(Bukkit.getMaxPlayers()));
    	
		
		if (type == TouchCommandType.CONSOLE) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), localCommand);
			
		} else if (type == TouchCommandType.OP) {
			boolean wasOp = player.isOp();
            
            try {
            	player.setOp(true);
            	player.chat("/" + localCommand);
            } finally {
            	player.setOp(wasOp);
            }
            
		} else if (type == TouchCommandType.SERVER) {
			BungeeConnector.sendToServer(player, localCommand);
			
		} else if (type == TouchCommandType.TELL) {
			player.sendMessage(localCommand);
			
		} else if (type == TouchCommandType.DEFAULT) {
			player.chat("/" + localCommand);
			
		} else {
			throw new IllegalStateException("Unhandled command type: " + type);
		}
	}
	
	
	public TouchCommandType getType() {
		return type;
	}
	
}
