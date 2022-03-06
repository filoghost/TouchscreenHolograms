/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.touch;

import me.filoghost.touchscreenholograms.bridge.BungeeConnector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
