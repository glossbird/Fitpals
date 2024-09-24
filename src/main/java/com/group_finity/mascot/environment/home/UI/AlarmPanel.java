package com.group_finity.mascot.environment.home.UI;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.environment.home.HomeUI;
import com.group_finity.mascot.glossbird.AlarmData;
import com.group_finity.mascot.glossbird.AlarmManager;
import com.group_finity.mascot.glossbird.AlarmSave;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AlarmPanel {

    JFrame alarmPanel;
    JButton button;
    JPanel panel;
    AlarmManager alarmManager;
    int hour = 12;
    int minute = 0;
    boolean enabled;
    boolean AM = false;
    JFormattedTextField hourLabel;
    JFormattedTextField minuteLabel;
    JLabel colon;
    JButton saveButton;
    private JLabel title;
    private JButton addnew;
    private JLabel On;
    private JLabel editLabel;
    private JPanel formPanel;
    private JPanel ToolBarPane;
    private JPanel BorderLayoutPanel;
    private JScrollPane ScrollPane;
    private JToolBar alarmBar;
    private JRadioButton EnabledButton;
    private JLabel Name;
    private JLabel Time;
    private JButton Edit;
    private JScrollBar scrollBar1;

    GroupLayout groupLayout;
    public  JToolBar GenerateBar(AlarmData alarm)
    {
        JToolBar bar = new JToolBar();
        bar.setLayout(new GridLayout(0,4));
        JRadioButton EnabledButton;
        JLabel nameLabel;

        JLabel timeLabel;
        JButton editButton;

        EnabledButton = new JRadioButton();
        EnabledButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmManager.ToggleAlarm(alarm.getId(),EnabledButton.isEnabled());
            }
        });
        if(alarm.isEnabled())
        {
            EnabledButton.setSelected(true);
        }
        nameLabel = new JLabel(alarm.getName());
        timeLabel = new JLabel(alarm.GetFormattedDate());
        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlarmEditing editTime = new AlarmEditing();
                editTime.setManager(alarmManager);
                alarmManager.SetActiveAlarmData(alarm.getId());
                editTime.run();
            }
        });

        bar.add(EnabledButton);
        bar.add(nameLabel);
        bar.add(timeLabel);
        bar.add(editButton);
        bar.setVisible(true);
        return bar;
    }

    JPanel refPanel;

    JPanel AllAlarms()
    {
        refPanel = null;
        JPanel panel = new JPanel();
        BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(box);
        for(AlarmData data : AlarmSave.getInstance().GetAllAlarms())
        {
            JToolBar testBar = GenerateBar(data);
            System.out.println("Adding bar " + data.getName());
            panel.add(testBar);
        }

        return panel;
    }

    public void RefreshVisuals()
    {
        frame.setVisible(false);
        refPanel = AllAlarms();
        ToolBarPane.validate();
        ToolBarPane.repaint();
        ScrollPane.setViewportView(refPanel);
        // ScrollPane.setSize(frame.getPreferredSize());
        ScrollPane.validate();
        frame.invalidate();
        frame.validate();

        frame.pack();
        frame.setSize(frame.getPreferredSize());
        frame.repaint();
        frame.setVisible(true);
    }

    JFrame frame;

    public  void run() {
        frame = new JFrame("AlarmPanel");
        frame.setContentPane(formPanel);
        addnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newId = alarmManager.CreateAlarm();
                RefreshVisuals();
                AlarmSave.getInstance().Save();
            }
        });
        RefreshVisuals();
        frame.setLocation(Main.getInstance().getHome().GetHomePosition().x-150,Main.getInstance().getHome().GetHomePosition().y);
        //frame.setVisible(true);
    }

    public AlarmPanel()
    {

    }

    public AlarmPanel(AlarmManager manager){

        this.alarmManager = manager;
        this.alarmManager.setAlarmPanel(this);
    }


    public void OpenPanel()
    {
        run();
       // alarmPanel.setVisible(true);
    }

}
