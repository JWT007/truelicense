/*
 * Copyright (C) 2005 - 2019 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */
package global.namespace.truelicense.build.tasks.generation;

import global.namespace.truelicense.obfuscate.ObfuscatedString;

/**
 * An immutable Velocity tool for use with Scala template files.
 */
public final class ScalaTool {

    public String obfuscatedString(String s) {
        return ObfuscatedString
                .obfuscate(s)
                .replace("new long[] { ", "Array[Long](")
                .replace(" }).toString()", "))");
    }
}
