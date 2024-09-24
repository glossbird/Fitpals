package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class SaveData {

    @JsonDeserialize(as = AlarmSave.class)
    public AlarmSave alarmSave;

    List<MascotData> SavedMascots;
    public SaveData()
    {
        super();
    }




    @JsonCreator
    public SaveData(@JsonProperty("alarmSave") AlarmSave data)
    {
        this.alarmSave =data;
    }

    public AlarmSave getAlarmSave() {
        return alarmSave;
    }

    public void setAlarmSave(AlarmSave alarmData) {
        this.alarmSave = alarmData;
    }
}

