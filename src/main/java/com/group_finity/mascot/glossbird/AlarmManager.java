package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmManager {

    public static Calendar alarmTime = Calendar.getInstance();
    public static Timer timer = new Timer();
    public static boolean enabled = false;
    int hour = 12;
    int minute = 0;
    public AlarmManager()
    {
        super();
        this.SetAlarmTime(alarmTime.get(Calendar.HOUR), alarmTime.get(Calendar.MINUTE)+1);
    }

    public void init()
    {
        enabled = false;

    }

    public void LoadAlarmTime()
    {

    }

    public void SetHour(int hour)
    {
        this.hour = hour;
        alarmTime.set(Calendar.HOUR, this.hour);
    }

    public void SetMinute(int minute)
    {
        this.minute = minute;
        alarmTime.set(Calendar.MINUTE, this.minute);
    }

    public int GetHour()
    {
        return this.hour;
    }

    public int GetMinute()
    {
        return this.minute;
    }







    public void Schedule()
    {

        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                Main.getInstance().getManager().setBehaviorAll("Alarm");
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
