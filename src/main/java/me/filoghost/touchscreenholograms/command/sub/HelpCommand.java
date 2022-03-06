/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.command.sub;

import me.filoghost.touchscreenholograms.Perms;
import me.filoghost.touchscreenholograms.command.CommandException;
import me.filoghost.touchscreenholograms.command.RootCommandHandler;
import me.filoghost.touchscreenholograms.command.SubCommand;
import me.filoghost.touchscreenholograms.utils.Format;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    private final RootCommandHandler commandHandler;

    public HelpCommand(RootCommandHandler commandHandler) {
        super("help");
        this.commandHandler = commandHandler;
        setPermission(Perms.MAIN_PERMISSION);
        setMinArguments(0);
        setUsage("[command]");
        setDescription("Lists all the possible commands.");

    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            for (SubCommand subCommand : commandHandler.getSubCommands()) {
                if (subCommand.getName().equalsIgnoreCase(args[0])) {
                    sender.sendMessage("");
                    sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Help about the command \"/touch " + subCommand.getName() + "\"");
                    sender.sendMessage(ChatColor.GRAY + subCommand.getDescription());
                    return;
                }
            }

            sender.sendMessage(ChatColor.RED + "Unknown sub-command. Type \"/touch help\" for a list of commands.");

        } else {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Touchscreen Holograms commands");
            for (SubCommand subCommand : commandHandler.getSubCommands()) {
                if (!(subCommand instanceof HelpCommand)) {
                    String usage = "/touch " + subCommand.getName() + (subCommand.getUsage().length() > 0 ? " " + subCommand.getUsage() : "");
                    sender.sendMessage(ChatColor.GREEN + usage);
                }
            }

            sender.sendMessage("");
            Format.sendTip(sender, "See help about a command with /touch help [command]");
        }
    }

}
