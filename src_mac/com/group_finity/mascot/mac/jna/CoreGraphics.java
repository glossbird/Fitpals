package com.group_finity.mascot.mac.jna;

public interface CoreGraphics extends Library {
    CoreGraphics INSTANCE = Native.load("CoreGraphics", CoreGraphics.class);

    CFArrayRef CGWindowListCopyWindowInfo(int option, int relativeToWindow);
}
