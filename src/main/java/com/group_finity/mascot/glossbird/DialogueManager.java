package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class DialogueManager {

    String activeMessage;
    int textPosition;
    JLabel visibleText;
    JLabel invisibleText;
    JFrame messageBox;
    boolean active;
    boolean stopCurrentMessage;
    float openTime;
    public DialogueManager()
    {
        super();
        activeMessage = "Test Message";
        textPosition = 0;
        active = false;
        stopCurrentMessage = false;
    }

    public String getActiveMessage() {
        return activeMessage;
    }

    public void safeSetActiveMessage(String activeMessage)
    {
        if(Objects.equals(this.activeMessage, activeMessage))
        {
            return;
        }
        setActiveMessage(activeMessage);
    }


    public void setActiveMessage(String activeMessage) {
        this.activeMessage = activeMessage;
        position = 0;
        openTime = 0;
        OpenTextbox();
    }

    public void StartTextbox()
    {
        messageBox = new JFrame();
        messageBox.setType(javax.swing.JFrame.Type.UTILITY);

        visibleText = new JLabel("");
        visibleText.setForeground(Color.black);
        invisibleText = new JLabel(activeMessage);
        invisibleText.setForeground(new Color(0,0,0,0));
        JPanel popupPanel = new JPanel(new GridLayout(2,1));
        popupPanel.add(visibleText,0);
        popupPanel.add(invisibleText,1);
        messageBox.setLocation(Main.getInstance().getMainMascot().getAnchor());
        messageBox.setSize(300,200);
        messageBox.add(popupPanel);
        messageBox.setUndecorated(true);

        messageBox.setVisible(true);
        active = true;
        if(!stopCurrentMessage)
        {
            //Typewriter(activeMessage);

        }
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
            if(typeNumb == 3)
            {
                typeNumb = 0;
                TypewriterInstance();
            }
        }
        else
        {
            if(messageBox.isVisible())
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
        messageBox.dispatchEvent(new WindowEvent(messageBox, WindowEvent.WINDOW_CLOSING));
        stopCurrentMessage = false;
        active = false;
    }


    void FollowMascot()
    {
        Point location = Main.getInstance().getMainMascot().getBounds().getLocation();
        if(Main.getInstance().getMainMascot().getEnvironment().getScreen().getRight() - location.x < 350)
        {
            location.x -= 250;
        }
        else
        {
            location.x += 50;
        }

        if(Math.abs(Main.getInstance().getMainMascot().getEnvironment().getScreen().getTop()+640 - location.y) < 100)
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
            StartTextbox();
            return;
        }
        if(position == activeMessage.length()+1)
        {
            active = false;
            return;
        }
        AudioManager.getInstance().playSound("ticksound");
        tempMessage = activeMessage.substring(0, position);
        secretMessage = activeMessage.substring(position);
        visibleText.setText(tempMessage);
        invisibleText.setText(secretMessage);
        position++;

    }

    public void Typewriter(String message)
    {
        String tempMessage = "";
        String secretMessage = message;
        for(int i = 1; i < message.length()+1; i++)
        {
            if(stopCurrentMessage)
            {
                i = message.length()+1;
                CloseMessage();
                StartTextbox();
                return;
            }
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
        active = false;

    }


}
