/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
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

    private final File file;
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

    public void trySaveToDisk() {
        trySaveToDisk(null);
    }

    public void trySaveToDisk(CommandSender sender) {
        try {
            saveToDisk();
        } catch (IOException ex) {
            ConsoleLogger.log(Level.SEVERE, "Unable to save " + file.getName() + " to disk!", ex);
            if (sender != null) {
                sender.sendMessage("I/O error: could not save " + file.getName() + " to disk.");
            }
        }
    }

}
