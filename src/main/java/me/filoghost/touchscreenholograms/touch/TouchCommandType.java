/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.touchscreenholograms.touch;

public enum TouchCommandType {

    DEFAULT(""),
    CONSOLE("console:"),
    OP("op:"),
    TELL("tell:"),
    SERVER("server:");

    private final String prefix;

    TouchCommandType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

}
