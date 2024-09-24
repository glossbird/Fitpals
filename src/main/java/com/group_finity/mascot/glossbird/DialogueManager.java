package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group_finity.mascot.Main;
import com.group_finity.mascot.Mascot;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogueManager {

    String activeMessage;
    int textPosition;
    JFrame messageBox;
    boolean active;
    boolean stopCurrentMessage;
    float openTime;

    boolean typewriterSound;
    boolean fullscreen;
    Mascot mainMascot;
    Map<String, SpeechList> speechMap = new HashMap<String,SpeechList>();

    LinkedList<String> previousSpeech;

    int TalkSpeed = 3;
    public DialogueManager()
    {
        super();
        activeMessage = "Test Message";
        textPosition = 0;
        active = false;
        stopCurrentMessage = false;
        previousSpeech = new LinkedList<String>();
        for(int i = 0; i < 5; i++)
        {
            previousSpeech.add("");
        }
    }

    public String getActiveMessage() {
        return activeMessage;
    }
    public List<String> allConditions()
    {
        if(speechMap == null)
        {
            return null;
        }
        return (List<String>) this.speechMap.keySet();
    }

    public boolean isTypewriterSound() {
        return typewriterSound;
    }

    public void setTypewriterSound(boolean typewriterSound) {
        this.typewriterSound = typewriterSound;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public void SetMainMascot(Mascot main)
    {
        this.mainMascot = main;
        try {
            LoadSpeechMessages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LoadSpeechMessages() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File path = new File(Main.IMAGE_DIRECTORY + "/" + mainMascot.getImageSet() + "/speech/speech.json");
        Map<String, SpeechList> result =  objectMapper.readValue(path, new TypeReference<Map<String, SpeechList>>() {
        });
        this.speechMap = result;
    }

    public List<Speech> SortSpeechListByCondition(List<Speech> speechList)
    {
        if(speechList == null || speechList.size() == 0)
        {
            return  null;
        }
        speechList.removeIf(speech -> speech.getConditionNumber() < 0);
        speechList.sort(Comparator.comparingInt(Speech::getConditionNumber).reversed());
        return  speechList;
    }

    public List<Speech> GetAllSpeechWithCondition(String id, String condition)
    {
        SpeechList literalList  = speechMap.get(id);
        if(literalList == null)
        {
            return  null;
        }
        List<Speech> speechList = literalList.speechList;

        List<Speech> newList = new ArrayList<>();
        for(Speech speech : speechList)
        {
            if(speech.getAltCondition().equals(condition))
            {
                newList.add(speech);
            }
        }

        return  newList;
    }



    public Speech LoadMessageOfCondition(String condition)
    {
        SpeechList literalList  = speechMap.get(condition);
        if(literalList == null)
        {
            return  null;
        }
        List<Speech> speechList = literalList.speechList;
        int random = (int) (Math.random() * (speechList.size()));
        return speechList.get(random);
    }

    public Speech GetSpeechFromCondition(String id, String condition)
    {
        SpeechList literalList  = speechMap.get(id);
        if(literalList == null)
        {
            return  null;
        }
        List<Speech> speechList = literalList.speechList;
        List<Speech> subList = new ArrayList<>();
        Speech toReturn = null;
        for(Speech speech : speechList)
        {
            if(speech.getAltCondition().equals(condition))
            {
                subList.add(speech);
            }
        }
        if(subList.size() > 1)
        {
            int random = (int) (Math.random() * (subList.size()));
            toReturn = subList.get(random);
        }
        else if(!subList.isEmpty()) {
            toReturn = subList.get(0);
        }

        return  toReturn;

    }


    public void EasterEggTrigger(String id, String condition)
    {
        System.out.println("Triggering easter egg for " + id + " doing " + condition);

        if(Objects.equals(condition, ""))
        {
            PlayRandomSpeech(id);
        }
        else
        {
            SpeechList literalList  = speechMap.get(id);
            if(literalList == null)
            {
                return;
            }
            List<Speech> speechList = literalList.speechList;
            List<Speech> subList = new ArrayList<>();
            for(Speech speech : speechList)
            {
                if(speech.getAltCondition().equals(condition))
                {
                    subList.add(speech);
                }
            }
            int random = (int) (Math.random() * (subList.size()));
            PlaySpeech(subList.get(random));
        }


    }

    String variable = "";

    public void PlayRandomSpeechWithVariable(String speechID, String variable)
    {
        this.variable = variable;
        Speech toSay = LoadMessageOfCondition(speechID);
        PlaySpeech(toSay);
    }


    public void PlayRandomSpeech(String speechID)
    {
        Speech toSay = LoadMessageOfCondition(speechID);
        PlaySpeech(toSay);

    }

    public String FillVariable(String var)
    {
        String output = "";
        switch(var)
        {
            case "window":
                output = mainMascot.getEnvironment().getActiveIETitle();
                break;
            case "alarmname":
                output = this.variable + "";
                break;
            default:
                output = "";
                break;
        }
        return output;
    }


    public void PlaySpeech(Speech speech)
    {
        if(speech == null)
        {
            System.out.println("Can't play null speech.");
            return;
        }
        else if(speech.variable!= null && !speech.variable.isEmpty())
        {
            String replacement = speech.getDialogue();
            replacement = replacement.replace("{0}",FillVariable(speech.getVariable()));
            speech.setDialogue(replacement);
        }
        safeSetActiveMessage(speech.dialogue);
        if(speech.audio_id != -1)
        {
            typewriterSound = false;
            AudioManager.getInstance().playSound(speech.condition+"_"+speech.audio_id);
        }
        else
        {
            typewriterSound = true;
        }
    }

    public void safeSetActiveMessage(String activeMessage)
    {
       // activeMessage =  formatText(activeMessage);
        if(Objects.equals(this.activeMessage, activeMessage))
        {
            return;
        }
        if(previousSpeech.contains(activeMessage))
        {
            return;
        }
        if(activeMessage.length() > 50)
        {
            TalkSpeed = 1;
        }
        else if (activeMessage.length() > 25)
        {
            TalkSpeed = 2;
        }
        else
        {
            TalkSpeed = 3;
        }
        setActiveMessage(activeMessage);
    }

    public void setActiveMessage(String activeMessage) {
        typewriterSound = true;
        this.activeMessage = activeMessage;
        previousSpeech.removeFirst();
        previousSpeech.add(activeMessage);
        position = 0;
        openTime = 0;
        OpenTextbox();
    }
    JXLabel label;
    public void StartTextbox()
    {
        messageBox = new JFrame();
        messageBox.setType(JFrame.Type.UTILITY);
        messageBox.setAlwaysOnTop(true);
        JPanel popupPanel = new JPanel(new FlowLayout());
        messageBox.setLocation(Main.getInstance().getMainMascot().getAnchor());
        messageBox.setSize(500,200);
        messageBox.add(popupPanel);
        messageBox.setUndecorated(true);
        label = new JXLabel(activeMessage);
        label.setFont(new Font("Courier", Font.BOLD,20));
        label.setLineWrap(true);
        label.setMaxLineSpan(250);
        label.setTextAlignment(JXLabel.TextAlignment.CENTER);
        popupPanel.add(label);
        Dimension dim = label.getPreferredSize();
        dim.setSize(dim.getWidth()+30, dim.getHeight()+15);
        messageBox.setSize(dim);
        label.setText("");
        messageBox.setVisible(true);
        //messageBox.toFront();
        active = true;
    }

    public void OpenTextbox()
    {
        if(active)
        {
            messageBox.setVisible(false);
            stopCurrentMessage = true;
        }
        else
        {
            if(messageBox != null)
            {
                CloseMessage();
            }

            StartTextbox();
        }
    }

    int typeNumb;

    public void tick()
    {
        if(active)
        {
            FollowMascot();
            typeNumb++;
            if(typeNumb == TalkSpeed)
            {
                typeNumb = 0;
                TypewriterInstance();
            }
        }
        else
        {
            if(messageBox != null && messageBox.isVisible())
            {
                openTime += .1f;
                if(openTime > 5f)
                {
                    CloseMessage();
                }
            }
        }
    }

    void CloseMessage()
    {
        //messageBox.dispatchEvent(new WindowEvent(messageBox, WindowEvent.WINDOW_CLOSING));
        messageBox.dispose();
        label = null;
        stopCurrentMessage = false;
        active = false;
    }


    void FollowMascot()
    {
        Point location = mainMascot.getBounds().getLocation();
        if(mainMascot.getEnvironment().getScreen().getRight() - location.x < 350)
        {
            location.x -= 250;
        }
        else
        {
            location.x += 50;
        }

        if(Math.abs(mainMascot.getEnvironment().getScreen().getTop()+640 - location.y) < 100)
        {
            location.y += 100;
        }
        else
        {
            location.y -= 100;
        }
        messageBox.setLocation(location);
    }

    String tempMessage = "";
    String secretMessage = "";
    int position = 0;
    public void TypewriterInstance()
    {
        if(stopCurrentMessage)
        {
            CloseMessage();
            variable = "";
            StartTextbox();
            return;
        }
        if(position == activeMessage.length()+1)
        {
            active = false;
            return;
        }
        tempMessage = activeMessage.substring(0, position);
        //messageBox.toFront();
        label.setText(tempMessage);
        position++;
        if(typewriterSound)
            AudioManager.getInstance().playSound("ticksound");
    }
}
