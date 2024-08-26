package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmManager {

    public static Calendar alarmTime = Calendar.getInstance();
    public static Timer timer = new Timer();

    public AlarmData getData() {
        return data;
    }

    public void setData(AlarmData data) {
        this.data = data;
    }

    AlarmData data;
    public AlarmManager()
    {
        super();
        data = new AlarmData(alarmTime.get(Calendar.HOUR), alarmTime.get(Calendar.MINUTE), false);
        this.init();
    }

    public void init()
    {
        data.enabled = false;
        LoadAlarmTime();

    }

    public void LoadAlarmTime()
    {
        try {
            this.data = SaveSystem.getInstance().Load(data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(data.enabled)
            this.SetAlarmTime(data.hour, data.minute);

    }

    public void SetHour(int hour)
    {
        this.data.hour = hour;
        alarmTime.set(Calendar.HOUR, this.data.hour);
    }

    public void SetMinute(int minute)
    {
        this.data.minute = minute;
        alarmTime.set(Calendar.MINUTE, this.data.minute);
    }

    public int GetHour()
    {
        return this.data.hour;
    }

    public int GetMinute()
    {
        return this.data.minute;
    }


    public void Schedule()
    {
        timer.purge();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                Main.getInstance().getManager().setBehaviorAll("Alarm");
                System.out.println("Alarm test");
            }

        }, alarmTime.getTime());
    }

    public void Save(AlarmData indata)
    {
        this.data = indata;
        Schedule();
        SaveSystem.getInstance().Save();
    }



    public void SetAlarmTime(int hour, int minute)
    {
        alarmTime.set(Calendar.HOUR, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);
        Schedule();
    }



}
