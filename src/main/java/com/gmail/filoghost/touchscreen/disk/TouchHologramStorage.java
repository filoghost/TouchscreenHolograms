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
package com.gmail.filoghost.touchscreen.disk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.filoghost.touchscreen.touch.TouchCommand;
import com.gmail.filoghost.touchscreen.touch.TouchHologram;
import com.gmail.filoghost.touchscreen.utils.ConsoleLogger;

public class TouchHologramStorage {

	private File file;
	private FileConfiguration config;
	
	public TouchHologramStorage(File file) {
		this.file = file;
	}
	
	public void load() throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public TouchHologram loadTouchHologram(String name) {
		List<String> commandsStrings = config.getStringList(name + ".commands");
		List<TouchCommand> commands = new ArrayList<TouchCommand>();
		
		for (String commandString : commandsStrings) {
			commands.add(TouchCommand.fromCommandString(commandString));
		}
		
		return new TouchHologram(name, commands);
	}
	
	public void deleteTouchHologram(TouchHologram hologram) {
		config.set(hologram.getLinkedHologramName(), null);
	}
	
	public void deleteTouchHologram(String hologramName) {
		config.set(hologramName, null);
	}
	
	public void saveTouchHologram(TouchHologram hologram) {
		List<String> tempList = new ArrayList<String>();
		
		for (TouchCommand command : hologram.getCommands()) {
			tempList.add(command.toCommandString());
		}
		
		config.set(hologram.getLinkedHologramName() + ".commands", tempList);
	}
	
	public Set<String> getTouchHolograms() {
		return config.getKeys(false);
	}
	
	public boolean isExistingTouchHologram(String name) {
		return config.isConfigurationSection(name);
	}
	
	public void saveToDisk() throws IOException {
		if (file != null && config != null) {
			config.save(file);
		}
	}
	
	public boolean trySaveToDisk() {
		return trySaveToDisk(null);
	}
	
	public boolean trySaveToDisk(CommandSender sender) {
		try {
			saveToDisk();
			return true;
		} catch (IOException ex) {
			ConsoleLogger.log(Level.SEVERE, "Unable to save " + file.getName() + " to disk!", ex);
			sender.sendMessage("I/O error: could not save " + file.getName() + " to disk.");
			return false;
		}
	}
	
}
