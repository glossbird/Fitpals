package com.group_finity.mascot.glossbird;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm {

    public static Calendar alarmTime = Calendar.getInstance();
    public static Timer timer = new Timer();
    public static boolean enabled = false;
    public Alarm()
    {

    }

    public void init()
    {
        enabled = false;

    }

    public void LoadAlarmTime()
    {

    }



    public void Schedule()
    {

        timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                System.out.println("Alarm test");
            }

        }, alarmTime.getTime());
    }

    public void Save()
    {

    }



    public void SetAlarmTime(int hour, int minute)
    {
        alarmTime.set(Calendar.HOUR, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);
        Schedule();
        Save();
    }



}
