package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;

import javax.swing.*;
import java.awt.*;

public class DialogueManager {

    String activeMessage;
    int textPosition;
    JLabel visibleText;
    JLabel invisibleText;
    JFrame messageBox;
    boolean active;
    public DialogueManager()
    {
        super();
        activeMessage = "Test Message";
        textPosition = 0;
        active = false;
    }

    public String getActiveMessage() {
        return activeMessage;
    }

    public void setActiveMessage(String activeMessage) {
        this.activeMessage = activeMessage;
    }

    public void OpenTextbox()
    {
        messageBox = new JFrame();
        visibleText = new JLabel("");
        visibleText.setForeground(Color.black);
        invisibleText = new JLabel(activeMessage);
        invisibleText.setForeground(new Color(0,0,0,0));
        JPanel popupPanel = new JPanel(new GridLayout(2,1));
        popupPanel.add(visibleText,0);
        popupPanel.add(invisibleText,1);
        messageBox.setLocation(300,500);
        messageBox.setSize(300,200);
        messageBox.add(popupPanel);
        messageBox.setVisible(true);
        active = true;
        Typewriter(activeMessage);
    }

    public void tick()
    {
        if(active)
            FollowMascot();
    }


    void FollowMascot()
    {
        Point location = Main.getInstance().getMainMascot().getBounds().getLocation();
        location.x += 50;
        location.y -= 100;
        messageBox.setLocation(location);
    }



    public void Typewriter(String message)
    {
        String tempMessage = "";
        String secretMessage = message;
        for(int i = 1; i < message.length()+1; i++)
        {

            tempMessage = message.substring(0, i);
            System.out.println(tempMessage);
            secretMessage = message.substring(i);
            visibleText.setText(tempMessage);
            invisibleText.setText(secretMessage);
            //Play Sound
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
