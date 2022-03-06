/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
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
