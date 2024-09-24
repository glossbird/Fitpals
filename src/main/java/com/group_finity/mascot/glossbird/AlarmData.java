package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.group_finity.mascot.Main;
import org.joda.time.DateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import java.util.*;

@JsonPropertyOrder({"hour","minute","enabled","name","sound","behavior","id"})
public class AlarmData {
    public int hour;
    public int minute;
    public boolean enabled;
    public String name;
    public String sound;
    public String behavior;
    public int id;

    @JsonIgnore
    private Audio audio;
    @JsonIgnore
    private DateTime alarm;
    @JsonIgnore
    private boolean alarmPlaying;
    @JsonIgnore
    private static Timer timer = new Timer();
    @JsonIgnore
    public static Calendar alarmTime = Calendar.getInstance();

    public AlarmData()
    {
        super();
        this.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.minute = Calendar.getInstance().get(Calendar.MINUTE);
        this.name = "New Alarm";
        this.enabled = false;
        this.sound = "";
        this.behavior = "";
        alarmPlaying = false;
    }

    public AlarmData(int hour, int minute, boolean enabled, String name, String sound, String behavior)
    {
        super();
        this.hour = hour;
        this.minute = minute;
        this.enabled = enabled;
        this.name = name;
        this.sound = sound;
        this.behavior = behavior;
        alarmPlaying = false;

    }

    public void Tick()
    {
        if(alarmPlaying)
        {
            if(Main.getInstance().eggMan.RecentActionExist("Behavior(Dragged)"))
            {
                audio.Stop();
            }
        }
    }

    public void init()
    {
        SetAlarmTime(this.getHour(), this.getMinute());
        alarmPlaying = false;


    }

    public int GetFormattedHour()
    {
        if(this.hour == 0)
        {
            return 12;
        }
        else if(this.hour > 0 && this.hour < 13)
        {
            return (this.hour);
        }
        else if(this.hour >= 13 && this.hour < 24)
        {
            return (this.hour-12);
        }
        return -1;
    }

    public String GetFormattedMinute()
    {
        return String.format("%02d", this.getMinute());
    }


    public String GetAmPM()
    {
        if(this.hour == 0)
        {
            return "PM";
        }
        else if(this.hour < 13)
        {
            return "AM";
        }
        else
        {
            return "PM";
        }
    }



    public String GetFormattedDate()
    {
        return this.GetFormattedHour() + ":" + this.GetFormattedMinute() + " " + this.GetAmPM();
    }

    public void SwitchAMPM()
    {
        if(this.hour > 12)
        {
            this.hour -= 12;
        }
        else
        {
            this.hour += 12;
            if(this.hour == 24)
            {
                this.hour = 0;
            }
        }
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

    public void Schedule()
    {
        timer.purge();
        if(enabled)
        {

            System.out.println("Scheduling for " + alarmTime.getTime());

            timer.schedule(new TimerTask() {
                @Override
                public void run()
                {
                    Main.getInstance().getManager().setBehaviorAll(getBehavior());
                    Main.getInstance().getDiagMan().PlayRandomSpeechWithVariable("reminder", getName());
                    audio = AudioManager.getInstance().playSound("alarm");
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


    public AlarmData(String JSONData)
    {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Made it here");
        AlarmData temp;
        try {
             temp = mapper.readValue(JSONData, AlarmData.class);
             this.hour = temp.hour;
             this.minute = temp.minute;
             this.enabled = temp.isEnabled();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public DateTime getAlarm() {
        return alarm;
    }

    public void setAlarm(DateTime alarm) {
        this.alarm = alarm;
    }

    @JsonCreator
    public AlarmData(@JsonProperty("hour") int hour,
    @JsonProperty("minute") int minute, @JsonProperty("enabled") boolean enabled,
                     @JsonProperty("name") String name, @JsonProperty("id") int id) {this.hour = hour; this.minute = minute; this.enabled = enabled; this.name = name; this.id=id;}



    @JsonProperty("alarm")
    private void unpackNested(Map<String,Object> alarm) {
        this.hour = (int)alarm.get("hour");
        this.minute = (int)alarm.get("minute");
        this.enabled = (boolean) alarm.get("enabled");
        this.name = (String) alarm.get("name");
        this.id = (int)alarm.get("id");
    }

}
