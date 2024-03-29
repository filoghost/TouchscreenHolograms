/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.command;

import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.command.sub.AddCommand;
import me.filoghost.touchscreenholograms.command.sub.ClearAllCommand;
import me.filoghost.touchscreenholograms.command.sub.DetailsCommand;
import me.filoghost.touchscreenholograms.command.sub.HelpCommand;
import me.filoghost.touchscreenholograms.command.sub.ListCommand;
import me.filoghost.touchscreenholograms.command.sub.RemoveCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class RootCommandHandler implements CommandExecutor {

    private final List<SubCommand> subCommands;

    public RootCommandHandler() {
        subCommands = Arrays.asList(
                new ClearAllCommand(),
                new ListCommand(),
                new AddCommand(),
                new RemoveCommand(),
                new DetailsCommand(),
                new HelpCommand(this)
        );
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
                } catch (CommandException e) {
                    sender.sendMessage(ChatColor.RED + e.getMessage());
                }
                return true;
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

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

}
