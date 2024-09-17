package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;
import org.joda.time.DateTime;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmManager {

    public static Calendar alarmTime = Calendar.getInstance();
    public DateTime alarm;
    public static Timer timer = new Timer();
    boolean alarmPlaying = false;
    Audio sound;
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

    void Tick()
    {
        if(alarmPlaying)
        {
            if(Main.getInstance().eggMan.RecentActionExist("Behavior(Dragged)"))
            {
                sound.Stop();
            }
        }
    }
    public void Schedule()
    {
        timer.purge();
        if(data.enabled)
        {

            System.out.println("Scheduling for " + alarmTime.getTime());

            timer.schedule(new TimerTask() {
                @Override
                public void run()
                {
                    Main.getInstance().getManager().setBehaviorAll("Alarm");
                    Main.getInstance().getDiagMan().PlayRandomSpeech("reminder");
                    sound = AudioManager.getInstance().playSound("alarm");
                    alarmPlaying = true;

                }

            }, dateFromDateTime(alarm));
        }

    }

    public Date dateFromDateTime(DateTime time)
    {
        Date date = new Date();
        date = time.toDate();
        return date;
    }

    public void Save(AlarmData indata)
    {
        this.data = indata;
        SetAlarmTime(this.data.hour, this.data.minute);
        Schedule();
        SaveSystem.getInstance().Save();
    }



    public void SetAlarmTime(int hour, int minute)
    {
        alarmTime.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);
        System.out.println("alarm time is " + alarmTime.getTime());
        System.out.println("Current time is " + DateTime.now().toDate());

        if(alarmTime.getTime().before(DateTime.now().toDate()))
        {
            alarmTime.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE)+1);
        }
        else
        {
            alarmTime.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
        }

        alarm = new DateTime(alarmTime);
        Schedule();
    }



}
