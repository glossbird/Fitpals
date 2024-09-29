package com.group_finity.mascot.mac.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.mac.CoreFoundation;

public interface CoreGraphics extends Library {
    CoreGraphics INSTANCE = Native.load("CoreGraphics", CoreGraphics.class);

    CoreFoundation.CFArrayRef CGWindowListCopyWindowInfo(int option, int relativeToWindow);
}
