package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group_finity.mascot.DebugWindow;
import com.group_finity.mascot.Main;
import com.group_finity.mascot.environment.home.UI.TimerPanel;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

public class TimerSystem {
    @JsonIgnore
    private Audio audio;
    @JsonIgnore
    private DateTime time;
    @JsonIgnore
    private boolean timerPlaying;
    @JsonIgnore
    private static java.util.Timer actualTimer = new java.util.Timer();
    @JsonIgnore
    public static Calendar alarmTime = Calendar.getInstance();
    @JsonIgnore
    public TimerPanel panel;

    public int minute;
    public int second;
    public boolean enabled;
    public String name;
    public String sound;
    public String behavior;


    
    public float TimeInSeconds;

    public TimerSystem()
    {
        super();
        this.minute = 15;
        this.second = 0;
        this.enabled = false;
        this.sound = "";
        this.behavior = "";
        timerPlaying = false;
    }

    public TimerSystem(int minute, int second, boolean enabled, String sound, String behavior)
    {
        super();
        this.minute = minute;
        this.second = second;
        this.enabled = enabled;
        this.sound = sound;
        this.behavior = behavior;
        timerPlaying = false;


    }

    public float getTimeInSeconds() {
        return TimeInSeconds;
    }

    public void setTimeInSeconds(float timeInSeconds) {
        TimeInSeconds = timeInSeconds;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
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
        if(behavior.isEmpty())
        {
            return "alarm";
        }
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }
    
    public void UpdateTime()
    {
        float TimeDelta;

        TimeDelta = alarmTime.getTimeInMillis() - DateTime.now().getMillis();

        panel.RefreshTimesFromDelta(TimeDelta);


    }

    public void SetPanel(TimerPanel panel)
    {
        this.panel = panel;
        panel.SetTimeText((minute),(second));
    }

    public void Stop()
    {
        actualTimer.purge();
        this.enabled = false;
        this.timerPlaying = false;
    }

    

    public void Tick()
    {
        if(enabled)
        {
            if(timerPlaying)
            {
                if(Main.getInstance().eggMan.RecentActionExist("Behavior(Dragged)"))
                {
                    audio.Stop();
                }
            }
            if(panel != null)
                UpdateTime();
        }
        else
        {
            if(panel != null)
                panel.RefreshText();
        }


    }

    public void init()
    {
       // SetAlarmTime(this.getMinute(), this.getSecond());
        timerPlaying = false;


    }

    public void ChangeTime(float amount)
    {
        if(Math.abs(amount) > 60)
        {
            this.minute += (int) (amount / 60);
            this.second += (int) (amount % 60);
        }
        else
        {
            this.second += (int) amount;
        }
        if(this.second >= 60)
        {
            this.minute+= 1;
            this.second = 0;
        }
        else if(this.second < 0)
        {
            this.minute -=1;
            this.second = 45;
        }

        if(this.minute < 0)
        {
            this.minute = 0;
        }
    }

    
    public void Start()
    {
        enabled = true;
        SetAlarmTime(minute, second);
    }

    public void SetAlarmTime(int minute, int second)
    {
        alarmTime.setTime(DateTime.now().toDate());
        alarmTime.set(Calendar.MINUTE, alarmTime.get(Calendar.MINUTE)+minute);
        alarmTime.set(Calendar.SECOND, alarmTime.get(Calendar.SECOND)+second);
        System.out.println("alarm time is " + alarmTime.getTime());
        System.out.println("Current time is " + DateTime.now().toDate());
        time = new DateTime(alarmTime);
        Schedule();
    }

    public void Schedule()
    {
        actualTimer.purge();
        if(enabled)
        {

            System.out.println("Scheduling for " + alarmTime.getTime());

            actualTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run()
                {
                    //Main.getInstance().getManager().setBehaviorAll(getBehavior());
                    Main.getInstance().getDiagMan().PlayRandomSpeechWithVariable("timer", getName());
                    audio = AudioManager.getInstance().playSound("alarm");
                    timerPlaying = true;
                    actualTimer.cancel();

                }

            }, dateFromDateTime(time), (minute*60 * 1000 + second*1000)*2);
        }



    }

    public Date dateFromDateTime(DateTime time)
    {
        Date date = new Date();
        date = time.toDate();
        return date;
    }

    @JsonCreator
    public TimerSystem(@JsonProperty("minute") int minute,
                       @JsonProperty("second") int second, @JsonProperty("enabled") boolean enabled,
                       @JsonProperty("name") String name) {this.minute = minute; this.second = second; this.enabled = enabled; this.name = name;}



    @JsonProperty("alarm")
    private void unpackNested(Map<String,Object> alarm) {
        this.minute = (int)alarm.get("minute");
        this.second = (int)alarm.get("second");
        this.enabled = (boolean) alarm.get("enabled");
        this.name = (String) alarm.get("name");
        this.behavior = (String) alarm.get("behavior");
        this.sound = (String) alarm.get("sound");
    }



}
