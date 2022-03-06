/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.utils;

import me.filoghost.touchscreenholograms.TouchscreenHolograms;

import java.util.logging.Level;

public class ConsoleLogger {

    public static void log(Level level, String msg, Throwable thrown) {
        TouchscreenHolograms.getInstance().getLogger().log(level, msg, thrown);
    }

    public static void log(Level level, String msg) {
        log(level, msg, null);
    }

}
