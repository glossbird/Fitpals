package com.group_finity.mascot.glossbird;

public class AlarmData {
    public int hour;
    public int second;
    public boolean enabled;

    public AlarmData(int hour, int second, boolean enabled)
    {
        this.hour = hour;
        this.second = second;
        this.enabled = enabled;
    }

    public AlarmData()
    {
        hour = 12;
        second = 0;
        enabled = false;
    }


}
