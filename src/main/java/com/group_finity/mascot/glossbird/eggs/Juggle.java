package com.group_finity.mascot.glossbird.eggs;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.behavior.UserBehavior;
import com.group_finity.mascot.environment.MascotEnvironment;
import com.group_finity.mascot.environment.home.HomeUI;
import com.group_finity.mascot.glossbird.EggManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Juggle {
    boolean TrinketRewarded;
    int juggleCount;
    JFrame juggleWindow;
    Mascot mainMascot;
    MascotEnvironment environment;
    EggManager manager;
    int finalScore;
    boolean hasTouchedGround;
    boolean heldTooLong;
    float dragTime;
    boolean dragging;
    public boolean juggling;
    boolean frameOpen;
    JLabel scoreLabel;
    JLabel scoreText;

    public Juggle(EggManager manager)
    {
        super();
        frameOpen = false;
        juggleCount = 0;
        finalScore = 0;
        this.manager = manager;
        hasTouchedGround = false;
        heldTooLong = false;

        dragTime = 0;
        dragging = false;
        juggling = true;
        System.out.println("Beginning juggle");
        JuggleLoop();
    }

    public void tick()
    {
        if(juggling)
        {
            JuggleTick();
        }
    }

    public void JuggleTick()
    {
        if(frameOpen)
        {
            RefreshFrame();
        }
        else if(juggleCount > 5)
        {
            OpenJuggleFrame();
        }
        if (environment.getFloor(true).isOn(mainMascot.getAnchor()))
        {
            hasTouchedGround = true;
        }
        else if(Objects.equals(mainMascot.getBehavior().toString(), "Behavior(Dragged)"))
        {
            if(dragging != true)
            {
                dragTime = 0;
                dragging = true;
            }
            else
            {
                dragTime += .1f;
            }
            if(dragging && dragTime > 3f)
            {
                heldTooLong = true;
            }
        }
        else
        {
            if(dragging)
            {
                dragging = false;
                juggleCount++;
            }
        }

        if(hasTouchedGround || heldTooLong)
        {
            juggling = false;
            if(mainMascot.getLinkedData() != null)
            {
                if(juggleCount > mainMascot.getLinkedData().HighScore_juggle)
                {
                    mainMascot.getLinkedData().HighScore_juggle = juggleCount;
                }
            }

            if(frameOpen)
            {
                RefreshFrame();
                juggleWindow.dispatchEvent(new WindowEvent(juggleWindow, WindowEvent.WINDOW_CLOSING));
                frameOpen = false;
            }
        }

    }

    void OpenJuggleFrame()
    {
        frameOpen = true;
        juggleWindow = new JFrame();
        JPanel panel = new JPanel(new GridBagLayout());
        scoreText = new JLabel("Score: ");
        scoreLabel = new JLabel(""+juggleCount);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        panel.add(scoreText,c);
        c.gridx = 1;
        panel.add(scoreLabel,c);
        juggleWindow.setTitle("Juggling!");
        juggleWindow.add(panel);
        juggleWindow.setSize(300,200);
        juggleWindow.setLocation(mainMascot.getBounds().getLocation());
        juggleWindow.setVisible(true);
    }

    void RefreshFrame()
    {
        String scoreString;
        if(!frameOpen)
        {
            return;
        }
        if(juggling)
        {
            scoreString = "" + juggleCount;
            String scoreStringTwo = "";
            if(juggleCount>25)
            {
                scoreStringTwo = "Nice juggle streak! Score: ";
                scoreText.setText(scoreStringTwo);
            }
            scoreLabel.setText(scoreString);

        }
        else
        {
            if(heldTooLong)
            {
                scoreLabel.setText("Held too long!");
            }
            if(hasTouchedGround)
            {
                scoreLabel.setText("Ground touched");
            }
        }
    }





    public void JuggleLoop()
    {
        mainMascot = Main.getInstance().getMainMascot();
        environment = mainMascot.getEnvironment();

        if (environment.getFloor(true).isOn(mainMascot.getAnchor()))
        {
            hasTouchedGround = true;

        }



    }

}
