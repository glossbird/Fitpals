package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@JsonPropertyOrder({"hour","minute","enabled"})
public class TimerData {
    public int hour;
    public int minute;
    public String sound;
    public String behavior;
    public boolean enabled;

    public TimerData()
    {
        super();
    }

    public TimerData(String JSONData)
    {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Made it here");
        TimerData temp;
        try {
             temp = mapper.readValue(JSONData, TimerData.class);
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @JsonCreator
    public TimerData(@JsonProperty("hour") int hour,
                     @JsonProperty("minute") int minute, @JsonProperty("enabled") boolean enabled) {this.hour = hour; this.minute = minute; this.enabled = enabled;}



    @SuppressWarnings("unchecked")
    @JsonProperty("timer")
    private void unpackNested(Map<String,Object> timer) {
        this.hour = (int)timer.get("hour");
        this.minute = (int)timer.get("minute");
        this.enabled = (boolean) timer.get("enabled");
    }

}
