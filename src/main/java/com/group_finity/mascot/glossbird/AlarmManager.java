package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.environment.home.UI.AlarmPanel;
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
    AlarmPanel alarmPanel;
    Audio sound;

    AlarmData activeData;

    public AlarmData getActiveData() {
        return activeData;
    }

    public AlarmPanel getAlarmPanel() {
        return alarmPanel;
    }

    public void setAlarmPanel(AlarmPanel alarmPanel) {
        this.alarmPanel = alarmPanel;
    }

    public void setActiveData(AlarmData activeData) {
        this.activeData = activeData;
    }

    public AlarmManager()
    {
        super();
        this.init();
    }

    public void init()
    {
        LoadAlarmTime();
    }

    public void ToggleAlarm(int id, boolean toggle)
    {
        AlarmData data = GetAlarmFromID(id);

        data.setEnabled(toggle);
        AlarmSave.getInstance().UpdateAlarm(data);
    }


    public void LoadAlarmTime()
    {
        try {
            SaveSystem.getInstance().Load();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for(AlarmData data : AlarmSave.getInstance().GetAllAlarms())
        {
            data.init();
        }

    }

    public void SetActiveAlarmData(int id)
    {
        this.activeData = GetAlarmFromID(id);
        System.out.println("Editing alarm with id " + id);
    }


    public AlarmData GetAlarmFromID(int id)
    {
        for (AlarmData data : AlarmSave.getInstance().GetAllAlarms())
        {
            if(data.getId() == id)
            {
                return data;
            }
        }
        return null;
    }

    public void RefreshPanel()
    {
        if(alarmPanel!= null)
        {
            alarmPanel.RefreshVisuals();
        }
    }

    public int CreateAlarm()
    {
        AlarmData newData = new AlarmData();
        AlarmSave.getInstance().AddAlarm(newData);
        newData.init();

        return  newData.getId();
    }


    public void SetHour(int hour)
    {
        this.activeData.hour = hour;
        alarmTime.set(Calendar.HOUR_OF_DAY, this.activeData.hour);
    }

    public void SetMinute(int minute)
    {
        this.activeData.minute = minute;
        alarmTime.set(Calendar.MINUTE, this.activeData.minute);
    }

    public int GetHour()
    {
        if(this.activeData == null)
        {
            return -1;
        }
        return this.activeData.hour;
    }

    public int GetMinute()
    {
        if(this.activeData == null)
        {
            return -1;
        }
        return this.activeData.minute;
    }

    public void tick()
    {
        for(AlarmData data : AlarmSave.getInstance().alarmDataList)
        {
            data.Tick();
        }
    }

    public void Save(AlarmData indata)
    {
        AlarmSave.getInstance().UpdateAlarm(indata);
        SaveSystem.getInstance().Save();
    }







}
