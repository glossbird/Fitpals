package com.group_finity.mascot.environment.home.UI;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.glossbird.AlarmData;
import com.group_finity.mascot.glossbird.AlarmManager;
import com.group_finity.mascot.glossbird.AudioManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Locale;

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

    public void UpdateAMPM() {
        PMRadioButton.setSelected(!manager.getActiveData().GetAmPM().equals("AM"));
        if (PMRadioButton.isSelected()) {
            PMRadioButton.setText("PM");
        } else {
            PMRadioButton.setText("AM");
        }
    }

    public void ToggleAMPM() {
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

        hourField.setEditor(new JSpinner.NumberEditor(hourField, "##"));
        DecimalFormat myFormatter = new DecimalFormat("00");
        minuteField.setEditor(new JSpinner.NumberEditor(minuteField, "00"));

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void Refresh(String field) {
        if (field.equals("hour")) {
            int trueHour = (Integer) hourField.getValue();
            if (PMRadioButton.isSelected()) {
                trueHour += 12;
                if (trueHour == 24) {
                    trueHour = 0;
                }
            }
            manager.getActiveData().setHour(trueHour);

        } else if (field.equals("minute")) {
            manager.getActiveData().setMinute((Integer) minuteField.getValue());

        } else if (field.equals("ampm")) {
            manager.getActiveData().SwitchAMPM();
        } else if (field.equals("name")) {
            manager.getActiveData().setName(nameField.getText());
        }

        RefreshTitle();
    }


    public void UpdateAlarmFromFields() {

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

    public void run() {
        this.pack();
        populateData(manager.getActiveData());
        this.setLocation(Main.getInstance().getHome().GetHomePosition().x - 100, Main.getInstance().getHome().GetHomePosition().y);
        this.setVisible(true);

        //System.exit(0);
    }

    AlarmManager manager;

    public void setManager(AlarmManager manager) {
        this.manager = manager;
    }

    public void RefreshTitle() {
        timePreview.setText(manager.getActiveData().GetFormattedDate());
        contentPane.validate();
        this.pack();
        this.validate();
    }


    public void populateData(AlarmData data) {
        System.out.println("Populating alarm " + data.getName());
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
                if (value < 0) {
                    value = 59;
                    minuteField.setValue(value);
                } else if (value > 59) {
                    value = 0;
                    minuteField.setValue(value);
                }
                minuteField.setEditor(new JSpinner.NumberEditor(minuteField, "00"));

                Refresh("minute");

            }
        });
        hourField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int) hourField.getValue();
                if (value == 0) {
                    value = 12;
                    ToggleAMPM();
                    hourField.setValue(value);
                } else if (value == 11) {
                    if (manager.getActiveData().hour == 12 || manager.getActiveData().hour == 0) {
                        ToggleAMPM();
                    }
                } else if (value == 12) {
                    ToggleAMPM();
                } else if (value > 12) {
                    value = 1;
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


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel1, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        buttonOK = new JButton();
        buttonOK.setText("SAVE");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(buttonOK, gbc);
        buttonCancel = new JButton();
        buttonCancel.setText("CANCEL");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(buttonCancel, gbc);
        deleteButton = new JButton();
        deleteButton.setText("DELETE");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(deleteButton, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel3, gbc);
        NamePanel = new JPanel();
        NamePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(NamePanel, gbc);
        nameLabel = new JLabel();
        nameLabel.setText("Name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        NamePanel.add(nameLabel, gbc);
        nameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        NamePanel.add(nameField, gbc);
        TimePanel = new JPanel();
        TimePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(TimePanel, gbc);
        timeLabel = new JLabel();
        timeLabel.setText("Time: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        TimePanel.add(timeLabel, gbc);
        hourField = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        TimePanel.add(hourField, gbc);
        colonLabel = new JLabel();
        colonLabel.setText(":");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        TimePanel.add(colonLabel, gbc);
        minuteField = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        TimePanel.add(minuteField, gbc);
        PMRadioButton = new JRadioButton();
        PMRadioButton.setText("PM");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        TimePanel.add(PMRadioButton, gbc);
        SoundPanel = new JPanel();
        SoundPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(SoundPanel, gbc);
        soundLabel = new JLabel();
        soundLabel.setText("Sound:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        SoundPanel.add(soundLabel, gbc);
        soundDropdown = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        SoundPanel.add(soundDropdown, gbc);
        BehaviorPanel = new JPanel();
        BehaviorPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(BehaviorPanel, gbc);
        behaviorLabel = new JLabel();
        behaviorLabel.setText("Behavior:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        BehaviorPanel.add(behaviorLabel, gbc);
        comboBox2 = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        BehaviorPanel.add(comboBox2, gbc);
        timePreview = new JLabel();
        Font timePreviewFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, timePreview.getFont());
        if (timePreviewFont != null) timePreview.setFont(timePreviewFont);
        timePreview.setHorizontalAlignment(2);
        timePreview.setHorizontalTextPosition(2);
        timePreview.setText("Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(timePreview, gbc);
        titleLabel = new JLabel();
        Font titleLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(4);
        titleLabel.setHorizontalTextPosition(4);
        titleLabel.setText("Editing:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        contentPane.add(titleLabel, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
