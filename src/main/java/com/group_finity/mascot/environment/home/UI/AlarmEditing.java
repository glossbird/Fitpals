package com.group_finity.mascot.environment.home.UI;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.glossbird.AlarmData;
import com.group_finity.mascot.glossbird.AlarmManager;
import com.group_finity.mascot.glossbird.AudioManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.text.DecimalFormat;

public class AlarmEditing extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameField;
    private JSpinner hourField;
    private JSpinner minuteField;
    private JRadioButton PMRadioButton;
    private JComboBox soundDropdown;
    private JComboBox comboBox2;
    private JLabel timePreview;
    private JPanel BehaviorPanel;
    private JPanel SoundPanel;
    private JPanel TimePanel;
    private JPanel NamePanel;
    private JLabel nameLabel;
    private JLabel timeLabel;
    private JLabel colonLabel;
    private JLabel soundLabel;
    private JLabel behaviorLabel;
    private JLabel titleLabel;
    private JButton deleteButton;

    public void UpdateAMPM()
    {
        PMRadioButton.setSelected(!manager.getActiveData().GetAmPM().equals("AM"));
        if(PMRadioButton.isSelected())
        {
            PMRadioButton.setText("PM");
        }
        else
        {
            PMRadioButton.setText("AM");
        }
    }

    public void ToggleAMPM()
    {
        Refresh("ampm");
        UpdateAMPM();
//        PMRadioButton.setSelected(!manager.getActiveData().GetAmPM().equals("AM"));
    }

    public AlarmEditing() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlarmDeleteConfirm deleteConfirm = new AlarmDeleteConfirm();
                deleteConfirm.run();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        hourField.setEditor(new JSpinner.NumberEditor(hourField,"##"));
        DecimalFormat myFormatter = new DecimalFormat("00");
        minuteField.setEditor(new JSpinner.NumberEditor(minuteField,"00"));

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void Refresh(String field)
    {
        if(field.equals("hour"))
        {
            int trueHour = (Integer) hourField.getValue();
            if(PMRadioButton.isSelected())
            {
                trueHour+=12;
                if(trueHour==24)
                {
                    trueHour = 0;
                }
            }
            manager.getActiveData().setHour(trueHour);

        }
        else if(field.equals("minute"))
        {
            manager.getActiveData().setMinute((Integer) minuteField.getValue());

        }
        else if(field.equals("ampm"))
        {
            manager.getActiveData().SwitchAMPM();
        }
        else if(field.equals("name"))
        {
            manager.getActiveData().setName(nameField.getText());
        }

        RefreshTitle();
    }



    public void UpdateAlarmFromFields()
    {

    }


    private void onOK() {
        // add your code here
        manager.Save(manager.getActiveData());
        manager.RefreshPanel();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public  void run() {
        this.pack();
        populateData(manager.getActiveData());
        this.setLocation(Main.getInstance().getHome().GetHomePosition().x-100,Main.getInstance().getHome().GetHomePosition().y);
        this.setVisible(true);

        //System.exit(0);
    }

    AlarmManager manager;

    public void setManager(AlarmManager manager)
    {
        this.manager = manager;
    }

    public void RefreshTitle()
    {
        timePreview.setText(manager.getActiveData().GetFormattedDate());
        contentPane.validate();
        this.pack();
        this.validate();
    }


    public void populateData(AlarmData data)
    {
        System.out.println("Populating alarm " +data.getName() );
        nameField.setText(data.getName());
        hourField.setValue(data.GetFormattedHour());
        //minuteField.setText(String.valueOf(data.GetFormattedMinute()));
        minuteField.setValue(data.getMinute());
        //minuteField.setEditor(new JSpinner.NumberEditor(minuteField,data.GetFormattedMinute()));

        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Refresh("name");
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }


        });

        minuteField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int) minuteField.getValue();
                if(value < 0)
                {
                    value = 59;
                    minuteField.setValue(value);
                }
                else if(value > 59)
                {
                    value = 0;
                    minuteField.setValue(value);
                }
                minuteField.setEditor(new JSpinner.NumberEditor(minuteField,"00"));

                Refresh("minute");

            }
        });
        hourField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int) hourField.getValue();
                if(value == 0)
                {
                    value = 12;
                    ToggleAMPM();
                    hourField.setValue(value);
                }
                else if(value ==11)
                {
                    if(manager.getActiveData().hour==12||manager.getActiveData().hour==0)
                    {
                        ToggleAMPM();
                    }
                }
                else if(value ==12)
                {
                    ToggleAMPM();
                }
                else if(value >12)
                {
                    value =1;
                    hourField.setValue(value);
                }

                Refresh("hour");
            }
        });

        PMRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Refresh("ampm");
                UpdateAMPM();

            }
        });
        UpdateAMPM();

        soundDropdown.addItem((data.getSound()));
        comboBox2.addItem((data.getSound()));
        RefreshTitle();
    }

}
