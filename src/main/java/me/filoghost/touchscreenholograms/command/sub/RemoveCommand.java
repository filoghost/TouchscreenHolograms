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
package me.filoghost.touchscreenholograms.command.sub;

import me.filoghost.touchscreenholograms.Perms;
import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.command.CommandException;
import me.filoghost.touchscreenholograms.command.CommandValidator;
import me.filoghost.touchscreenholograms.command.SubCommand;
import me.filoghost.touchscreenholograms.disk.TouchHologramStorage;
import me.filoghost.touchscreenholograms.touch.TouchCommand;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import me.filoghost.touchscreenholograms.touch.TouchManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RemoveCommand extends SubCommand {

    public RemoveCommand() {
        super ("remove", "removecmd", "removecommand");
        setPermission(Perms.MAIN_PERMISSION);
        setMinArguments(2);
        setUsage("<hologram> <commandIndex>");
        setDescription("Removes a command at a given index.");
    }


    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        TouchManager touchManager = TouchscreenHolograms.getTouchManager();
        TouchHologramStorage fileStorage = TouchscreenHolograms.getFileStorage();

        TouchHologram touchHologram = touchManager.getByName(args[0]);
        CommandValidator.notNull(touchHologram, "There are no commands associated with that hologram. The hologram name is case sensitive.");

        int realIndex = CommandValidator.getInteger(args[1]) - 1;
        CommandValidator.isTrue(0 <= realIndex && realIndex < touchHologram.getCommands().size(),
                "Invalid command index: must be between 1 and " + touchHologram.getCommands().size() + ".\n" +
                "Find the correct index with \"/th listcommands " + touchHologram.getLinkedHologramName() + "\".");

        TouchCommand removedCommand = touchHologram.getCommands().remove(realIndex);

        if (touchHologram.getCommands().size() > 0) {
            fileStorage.saveTouchHologram(touchHologram);
        } else {
            touchManager.remove(touchHologram);
            touchManager.refreshHolograms();
            fileStorage.deleteTouchHologram(touchHologram);
        }

        sender.sendMessage(ChatColor.GREEN + "Removed the command " + ChatColor.GRAY + removedCommand.toCommandString() + ChatColor.GREEN + " from the touch hologram.");
        fileStorage.trySaveToDisk(sender);
    }

}
