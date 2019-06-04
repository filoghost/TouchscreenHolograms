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

import java.io.File;
import java.io.IOException;

public class Settings extends ConfigBase {
	
	private static String NODE_UPDATE_NOTIFICATION = "update-notification";
	
	public boolean updateNotification = true;
	
	
	public Settings(File file) {
		super(file);
	}
	
	@Override
	public void load() throws IOException {
		super.load();
		
		boolean needSave = false;
		
		if (config.isSet(NODE_UPDATE_NOTIFICATION)) {
			updateNotification = config.getBoolean(NODE_UPDATE_NOTIFICATION);
		} else {
			config.set(NODE_UPDATE_NOTIFICATION, updateNotification);
			needSave = true;
		}
		
		if (needSave) {
			saveToDisk();
		}
	}
	
}
