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
package com.gmail.filoghost.touchscreen.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Format {
	
	public static void sendWarning(CommandSender recipient, String warning) {
		recipient.sendMessage(ChatColor.RED + "( " + ChatColor.DARK_RED + ChatColor.BOLD + "!" + ChatColor.RESET + ChatColor.RED + " ) " + ChatColor.GRAY + warning);
	}
	
	public static void sendTip(CommandSender recipient, String tip) {
		recipient.sendMessage(ChatColor.WHITE + "[TIP] " + ChatColor.GRAY + tip);
	}

}
