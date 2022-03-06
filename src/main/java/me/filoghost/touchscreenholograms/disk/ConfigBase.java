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
package me.filoghost.touchscreenholograms.disk;

import me.filoghost.touchscreenholograms.utils.ConsoleLogger;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigBase {

    protected File file;
    protected FileConfiguration config;

    public ConfigBase(File file) {
        this.file = file;
    }

    public void load() throws IOException {
        if (!file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
        if (!file.isFile()) {
            file.createNewFile();
        }
        config = YamlConfiguration.loadConfiguration(file);
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
            if (sender != null) {
                sender.sendMessage("I/O error: could not save " + file.getName() + " to disk.");
            }
            return false;
        }
    }

}
