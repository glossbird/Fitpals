package com.group_finity.mascot.environment.home.UI;

import javax.swing.*;

public class TimerPanel {
    private JPanel Clock;
    private JLabel Hour;
    private JLabel colon;
    private JLabel Minute;
    private JButton SetSound;
    private JButton SetBehavior;
    private JButton TimeUp;
    private JButton TimeDown;
    private JButton Start;
    private JLabel Timer;
    private JPanel contentPane;

    public TimerPanel()
    {
        run();
    }

    public void run() {
        JFrame frame = new JFrame("TimerPanel");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void RefreshTimes()
    {

    }

}
