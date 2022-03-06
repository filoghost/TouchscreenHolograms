/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.command;

public class CommandValidator {

    public static void notNull(Object obj, String string) throws CommandException {
        if (obj == null) {
            throw new CommandException(string);
        }
    }

    public static void isTrue(boolean b, String string) throws CommandException {
        if (!b) {
            throw new CommandException(string);
        }
    }

    public static int getInteger(String integer) throws CommandException {
        try {
            return Integer.parseInt(integer);
        } catch (NumberFormatException ex) {
            throw new CommandException("Invalid number: '" + integer + "'.");
        }
    }

}
