package com.group_finity.mascot.environment.home.UI;

import com.group_finity.mascot.environment.home.HomeUI;
import com.group_finity.mascot.glossbird.AlarmManager;

import javax.swing.*;
import java.awt.*;


public class AlarmPanel {

    JFrame alarmPanel;
    AlarmManager alarmManager;

    public AlarmPanel(){
        super();
        alarmPanel = new JFrame("Alarm");

        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);
        alarmPanel.getContentPane().add(panel);
        panel.setLayout(layout);
        alarmPanel.setSize(200,150);
        JButton button = new JButton("Alarm");
        Rectangle boundsTwo = HomeUI.getMaxWindowBounds(alarmPanel);
        alarmPanel.setLocation(boundsTwo.x + boundsTwo.width - alarmPanel.getWidth(), boundsTwo.y + boundsTwo.height - alarmPanel.getHeight());

        JTextField hourLabel = new JTextField("12");
        JLabel colon = new JLabel(":");
        JTextField minuteLabel = new JTextField("00");
        JLabel AMPM = new JLabel("PM");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 2;
        c.gridx = 0;

        JButton hourUp = new JButton();
        JButton hourDown = new JButton();
        JButton minuteUp = new JButton();
        JButton minuteDown = new JButton();

        JButton saveButton =  new JButton("Save");
        JCheckBox enabledBox = new JCheckBox("Enable Alarm");
        c.gridy = 1;
        panel.add(hourLabel,c );
        c.gridx = 1;
        panel.add(colon,c );
        c.gridx = 2;
        panel.add(minuteLabel,c );
        c.gridx = 3;
        panel.add(AMPM,c );
        c.gridwidth =1;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridy = 2;
        c.gridx = 0;
        panel.add(enabledBox,c);

    }

    public void FormatDateTime()
    {

    }


    public void OpenPanel()
    {
        alarmPanel.setVisible(true);
    }

}
