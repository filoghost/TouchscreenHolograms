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

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.gmail.filoghost.holographicdisplays.object.NamedHologram;
import com.gmail.filoghost.holographicdisplays.object.NamedHologramManager;

import me.filoghost.touchscreenholograms.Perms;
import me.filoghost.touchscreenholograms.TouchscreenHolograms;
import me.filoghost.touchscreenholograms.command.CommandException;
import me.filoghost.touchscreenholograms.command.CommandValidator;
import me.filoghost.touchscreenholograms.command.SubCommand;
import me.filoghost.touchscreenholograms.disk.TouchHologramStorage;
import me.filoghost.touchscreenholograms.touch.TouchCommand;
import me.filoghost.touchscreenholograms.touch.TouchHologram;
import me.filoghost.touchscreenholograms.touch.TouchManager;
import me.filoghost.touchscreenholograms.utils.Format;

public class AddCommand extends SubCommand {

    public AddCommand() {
        super("add", "addcmd", "addcommand");
        setPermission(Perms.MAIN_PERMISSION);
        setMinArguments(2);
        setUsage("<hologram> <command>");
        setDescription("Adds a command to a hologram.");
    }


    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        TouchManager touchManager = TouchscreenHolograms.getTouchManager();
        TouchHologramStorage fileStorage = TouchscreenHolograms.getFileStorage();

        TouchHologram touch = touchManager.getByName(args[0]);

        if (touch == null) {
            NamedHologram hologram = NamedHologramManager.getHologram(args[0]);
            CommandValidator.notNull(hologram, "There's no hologram with that name. The name is case sensitive.");

            touch = new TouchHologram(hologram.getName());
            touchManager.add(touch);
            touchManager.refreshHolograms();

            if (hologram.size() == 0) {
                Format.sendWarning(sender, "The hologram has no lines!");
            } else {
                if (hologram.size() > 1) {
                    Format.sendWarning(sender, "You selected a hologram that contains more than one line: it's recommanded to use holograms with a single line, because the click wouldn't be detected correctly in the other lines.");
                }

                // If the text is too big for the touch hitbox, send a warning message
                if (hologram.getLine(0) instanceof TextLine) {
                    String text = ChatColor.stripColor(((TextLine) hologram.getLine(0)).getText());

                    if (text.length() > 6) {
                        Format.sendWarning(sender, "You selected a hologram with the first line longer than 6 chars. Since the holograms rotate (client side), you should reduce the length, because the click wouldn't be detected correctly.");
                    }
                }
            }
        }

        String command = StringUtils.join(args, " ", 1, args.length);
        if (command.startsWith("/")) {
            Format.sendWarning(sender,"You used a slash before the command, is that an error? For normal commands, do not include the slash.");
        }

        touch.getCommands().add(TouchCommand.fromCommandString(command));

        fileStorage.saveTouchHologram(touch);

        sender.sendMessage(ChatColor.GREEN + "Command added to the hologram!");
        fileStorage.trySaveToDisk(sender);
    }

}
