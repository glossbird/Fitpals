package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@JsonPropertyOrder({"hour","minute","enabled"})
public class AlarmData {
    public int hour;
    public int minute;
    public boolean enabled;

    public AlarmData()
    {
        super();
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @JsonCreator
    public AlarmData(@JsonProperty("hour") int hour,
    @JsonProperty("minute") int minute, @JsonProperty("enabled") boolean enabled) {this.hour = hour; this.minute = minute; this.enabled = enabled;}



    @SuppressWarnings("unchecked")
    @JsonProperty("alarm")
    private void unpackNested(Map<String,Object> alarm) {
        this.hour = (int)alarm.get("hour");
        this.minute = (int)alarm.get("minute");
        this.enabled = (boolean) alarm.get("enabled");
    }

}
