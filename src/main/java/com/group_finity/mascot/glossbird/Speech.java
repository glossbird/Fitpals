package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Mascot;

import javax.swing.*;

public class Speech {

    String name;
    String dialogue;
    int conditionNumber;
    String condition;
    String altCondition;
    int audio_id;
    String mp3String;
    String behavior;
    public Speech()
    {
        super();
    }


    public Speech(String dialogue, String condition, int audio_id)
    {
        super();
        this.dialogue = dialogue;
        this.condition = condition;
        this.audio_id = audio_id;
        this.mp3String = condition + "_" + audio_id;
        this.conditionNumber = -1;
        this.altCondition = "";
        this.behavior = "";
    }

    public String getMp3String()
    {
        return this.mp3String;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public int getConditionNumber() {
        return conditionNumber;
    }

    public void setConditionNumber(int conditionNumber) {
        this.conditionNumber = conditionNumber;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAltCondition() {
        return altCondition;
    }

    public void setAltCondition(String altCondition) {
        this.altCondition = altCondition;
    }

    public int getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(int audio_id) {
        this.audio_id = audio_id;
    }

    public void setMp3String(String mp3String) {
        this.mp3String = mp3String;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }
}
