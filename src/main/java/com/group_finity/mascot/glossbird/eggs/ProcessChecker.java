package com.group_finity.mascot.glossbird.eggs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProcessChecker {
    float timeOpen;
    float timeFocus;
    String handle;
    String name;
    boolean open;
    boolean focused;
    boolean fullscreen;

    boolean checked;
    boolean newOpened;
    boolean newClosed;
    public ProcessChecker(String exe)
    {
        super();
        this.timeOpen = 0;
        this.timeFocus = 0;
        this.handle = exe;
        this.name = exe;
        this.open = true;
        this.focused = false;
        this.fullscreen = false;
        this.checked = true;
        this.newOpened = true;
        this.newClosed = false;
    }

    public String ToString()
    {
        return "Process: " + this.handle + ": Time running: " + this.getTimeOpen() + " Open: " + this.isOpen() + " Focus: " + this.isFocused() + " New Open: " + this.isNewOpened() + " New Close: " + this.isNewClosed();
    }

    public boolean isNewClosed() {
        return newClosed;
    }

    public void setNewClosed(boolean newClosed) {
        this.newClosed = newClosed;
    }

    public boolean isNewOpened() {
        return newOpened;
    }

    public void setNewOpened(boolean newOpened) {
        this.newOpened = newOpened;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public float getTimeFocus() {
        return timeFocus;
    }

    public void setTimeFocus(float timeFocus) {
        this.timeFocus = timeFocus;
    }

    public void increaseTimeFocus(float timeDelta)
    {
        this.timeFocus += timeDelta;
    }


    public float getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(float timeOpen) {
        this.timeOpen = timeOpen;
    }

    public void increaseTimeOpen(float timeDelta)
    {
        this.timeOpen+= timeDelta;
    }


    public String getHandle() {
        return handle.toLowerCase();
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

}
