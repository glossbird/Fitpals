package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.group_finity.mascot.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlarmSave {

    public static AlarmSave instance = new AlarmSave();


    public List<AlarmData> alarmDataList;

    @JsonDeserialize(as = AlarmSave.class)
    public static AlarmSave getInstance() {
        return instance;
    }


    public AlarmSave()
    {
        this.alarmDataList = new ArrayList<>();
    }


    public AlarmSave(String JSONData)
    {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Made it here");
        AlarmSave temp;
        try {
            temp = mapper.readValue(JSONData, AlarmSave.class);
            this.alarmDataList = temp.alarmDataList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public void UpdateAlarm(AlarmData data)
    {
        for(AlarmData arrayData : alarmDataList)
        {
            if(arrayData.getId() == data.getId())
            {
                arrayData = data;
                break;
            }
        }
        Save();
    }

    public void AddAlarm(AlarmData data)
    {
        data.setId(this.alarmDataList.size());
        this.alarmDataList.add(data);
    }


    public void Save()
    {
        SaveSystem.getInstance().data.setAlarmSave(this);
        SaveSystem.getInstance().Save();
    }


    public void LoadAlarms(AlarmSave alarmList)
    {
        this.alarmDataList = alarmList.alarmDataList;
    }


    public AlarmData[] GetAllAlarms()
    {
        if(alarmDataList == null)
        {
            return null;
        }
        AlarmData[] arrayData = (AlarmData[]) alarmDataList.toArray(new AlarmData[alarmDataList.size()]);
        System.out.println("Returning " + arrayData.length + " alarms");
        return arrayData;
    }

}
