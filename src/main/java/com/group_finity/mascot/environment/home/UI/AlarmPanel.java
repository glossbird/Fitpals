package com.group_finity.mascot.environment.home.UI;

import com.group_finity.mascot.environment.home.HomeUI;
import com.group_finity.mascot.glossbird.AlarmData;
import com.group_finity.mascot.glossbird.AlarmManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AlarmPanel {

    JFrame alarmPanel;
    AlarmManager alarmManager;
    int hour = 12;
    int minute = 0;
    boolean enabled;
    boolean AM = false;
    public AlarmPanel(AlarmManager manager){
        super();
        this.alarmManager = manager;
        alarmPanel = new JFrame("Alarm");

        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);
        alarmPanel.getContentPane().add(panel);
        panel.setLayout(layout);
        alarmPanel.setSize(200,150);
        JButton button = new JButton("Alarm");
        Rectangle boundsTwo = HomeUI.getMaxWindowBounds(alarmPanel);
        alarmPanel.setLocation(boundsTwo.x + boundsTwo.width - alarmPanel.getWidth(), boundsTwo.y + boundsTwo.height - alarmPanel.getHeight());

        JFormattedTextField hourLabel = new JFormattedTextField(this.hour);
        hourLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hour = Integer.parseInt(hourLabel.getText());
            }
        });
        JLabel colon = new JLabel(":");
        JFormattedTextField minuteLabel = new JFormattedTextField(minute);
        minuteLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minute = Integer.parseInt(minuteLabel.getText());
            }
        });

        DocumentListener dl = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldState();
            }

            protected void updateFieldState() {
                FormatDateTime(hourLabel,minuteLabel);
            }
        };

        hourLabel.getDocument().addDocumentListener(dl);
        minuteLabel.getDocument().addDocumentListener(dl);


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
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmManager.Save(new AlarmData(hour,minute,enabled));
            }
        });
        JCheckBox amBox = new JCheckBox("AM/PM");
        amBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(amBox.isSelected())
                {
                    AM = true;
                }
                else
                {
                    AM = false;
                }

            }
        });
        JCheckBox enabledBox = new JCheckBox("Enable Alarm");
        enabledBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(enabledBox.isSelected())
                {
                    enabled = true;
                }
                else
                {
                    enabled = false;
                }

            }
        });
        c.gridy = 1;
        panel.add(hourLabel,c );
        c.gridx = 1;
        panel.add(colon,c );
        c.gridx = 2;
        panel.add(minuteLabel,c );
        c.gridx = 3;
        panel.add(AMPM,c );
        c.gridy = 2;
        panel.add(amBox,c);
        c.gridwidth =1;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridy = 2;
        c.gridx = 0;
        panel.add(enabledBox,c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(saveButton);

        hour = alarmManager.GetHour();
        hourLabel.setText(String.valueOf(hour));
        minute = alarmManager.GetMinute();
        minuteLabel.setText(String.valueOf(minute));

    }

    public void FormatDateTime(JTextField hourField, JTextField minField)
    {
        if(hourField.getText() != "" && hourField.getText() != null)
        {
            try{
                this.hour = Integer.parseInt(hourField.getText());
            } catch (Exception e)
            {

            }
        }
              if(minField.getText() != "" && minField.getText() != null)
        {
            try{
                this.minute = Integer.parseInt(minField.getText());
            } catch (Exception e)
            {

            }
        }
    }


    public void OpenPanel()
    {
        alarmPanel.setVisible(true);
    }

}
