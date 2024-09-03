package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class SaveData {

    @JsonDeserialize(as = AlarmData.class)
    public AlarmData alarmData;

    List<MascotData> SavedMascots;
    public SaveData()
    {
        super();
    }




    @JsonCreator
    public SaveData(@JsonProperty("alarmData") AlarmData data)
    {
        this.alarmData =data;
    }

    public AlarmData getAlarmData() {
        return alarmData;
    }

    public void setAlarmData(AlarmData alarmData) {
        this.alarmData = alarmData;
    }
}

