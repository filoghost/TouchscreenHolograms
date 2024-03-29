/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms;

import me.filoghost.touchscreenholograms.bridge.HolographicDisplaysHelper;
import me.filoghost.touchscreenholograms.command.RootCommandHandler;
import me.filoghost.touchscreenholograms.disk.Settings;
import me.filoghost.touchscreenholograms.disk.TouchHologramStorage;
import me.filoghost.touchscreenholograms.listener.EventListener;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import me.filoghost.touchscreenholograms.touch.TouchManager;
import me.filoghost.touchscreenholograms.utils.ConsoleLogger;
import me.filoghost.updatechecker.UpdateChecker;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class TouchscreenHolograms extends JavaPlugin {

    private static TouchscreenHolograms instance;
    private static Settings settings;
    private static TouchHologramStorage fileStorage;
    private static TouchManager touchManager;
    private static String newVersion;

    @Override
    public void onEnable() {
        instance = this;

        // Load the settings
        settings = new Settings(new File(getDataFolder(), "config.yml"));
        try {
            settings.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            printErrorAndDisable(
                "******************************************************",
                "     Could not load config.yml.",
                "     " + getDescription().getName() + " will be disabled.",
                "******************************************************"
            );
            return;
        }

        // Check for updates
        if (settings.updateNotification) {
            UpdateChecker.run(this, 77049, (String newVersion) -> {
                TouchscreenHolograms.newVersion = newVersion;
                ConsoleLogger.log(Level.INFO, "Found a new version available: " + newVersion);
                ConsoleLogger.log(Level.INFO, "Download it on Bukkit Dev:");
                ConsoleLogger.log(Level.INFO, "https://dev.bukkit.org/projects/touchscreen-holograms");
            });
        }

        // Load the database
        fileStorage = new TouchHologramStorage(new File(getDataFolder(), "database.yml"));
        try {
            fileStorage.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            printErrorAndDisable(
                "******************************************************",
                "     Could not load database.yml.",
                "     " + getDescription().getName() + " will be disabled.",
                "******************************************************"
            );
            return;
        }

        // Register the touch holograms into the TouchManager
        touchManager = new TouchManager();
        for (String touchHologramName : fileStorage.getTouchHolograms()) {
            touchManager.add(fileStorage.loadTouchHologram(touchHologramName));
        }

        // bStats metrics
        int pluginID = 3705;
        new MetricsLite(this, pluginID);

        // Delay loading because:
        // * "softdepend" in plugin.yml can break
        // * Holographic Displays loads holograms in a delayed task
        Bukkit.getScheduler().runTaskLater(this, () -> {
            // Check that Holographic Displays is present and enabled
            if (!HolographicDisplaysHelper.init()) {
                printErrorAndDisable(
                        "******************************************************",
                        "     " + getDescription().getName() + " requires the plugin",
                        "     HolographicDisplays v3.0+ enabled to run.",
                        "     This plugin will be disabled.",
                        "******************************************************"
                );
                return;
            }

            // Set the command handler
            getCommand("touch").setExecutor(new RootCommandHandler());

            // Register events
            Bukkit.getPluginManager().registerEvents(new EventListener(), this);
            HolographicDisplaysHelper.registerChangeListener(touchManager::refreshHolograms, this);

            // Check that holograms still exist in Holographic Displays
            boolean fileStorageUpdated = false;

            for (TouchHologram touchHologram : touchManager.getTouchHolograms()) {
                String name = touchHologram.getLinkedHologramName();
                if (!HolographicDisplaysHelper.hologramExists(name)) {
                    fileStorage.deleteTouchHologram(name);
                    fileStorageUpdated = true;
                    ConsoleLogger.log(Level.WARNING, "Cannot find the hologram '" + name + "'. It was probably deleted from HolographicDisplays, commands have been removed too.");
                }
            }

            if (fileStorageUpdated) {
                fileStorage.trySaveToDisk();
            }

            // Update touch handlers
            touchManager.refreshHolograms();

        }, 20L);
    }

    private void printErrorAndDisable(String... messageLines) {
        StringBuilder message = new StringBuilder("\n ");
        for (String messageLine : messageLines) {
            message.append('\n');
            message.append(messageLine);
        }
        message.append('\n');
        System.out.println(message);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}
        setEnabled(false);
    }

    public static TouchscreenHolograms getInstance() {
        return instance;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static TouchHologramStorage getFileStorage() {
        return fileStorage;
    }

    public static TouchManager getTouchManager() {
        return touchManager;
    }

    public static String getNewVersion() {
        return newVersion;
    }

}
