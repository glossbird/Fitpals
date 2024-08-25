package com.group_finity.mascot.environment.home;

import com.group_finity.mascot.environment.home.UI.AlarmPanel;
import com.group_finity.mascot.glossbird.Screenshot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HomeUI {

    Rectangle bounds;
    HomeArea areaLink;
    AlarmPanel alarmPanel;
    Screenshot screenshot;

    public HomeUI()
    {
        super();
        JFrame frame = new JFrame("Home");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());

        JTextField textField = new JTextField(50);
        JLabel homeLabel = new JLabel("Home");
        JButton alarmButton = new JButton("Alarm");
        JButton trinkets = new JButton("Trinkets");
        JButton selfie = new JButton("Selfie");
        alarmButton.setSize(200,100);
        selfie.setSize(100,100);
        screenshot = new Screenshot();
        selfie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    screenshot.MakeScreenshot();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        alarmPanel = new AlarmPanel();
        alarmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                alarmPanel.OpenPanel();

            }

        });
        contentPane.add(homeLabel, BorderLayout.CENTER);
        contentPane.add(alarmButton);
        contentPane.add(selfie);
        Rectangle boundsTwo = getMaxWindowBounds(frame);

        bounds = new Rectangle(100, 100, 300, 400);
        frame.setBounds(bounds);
        frame.setLocation(boundsTwo.x + boundsTwo.width - frame.getWidth(), boundsTwo.y + boundsTwo.height - frame.getHeight());
        frame.setUndecorated(true);



        frame.setVisible(true);
        areaLink = new HomeArea(this);
    }

    public static Rectangle getMaxWindowBounds(JFrame frame) {
        GraphicsConfiguration config = frame.getGraphicsConfiguration();
        Rectangle boundsTwo = config.getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
        boundsTwo.x += insets.left;
        boundsTwo.y += insets.top;
        boundsTwo.width -= insets.left + insets.right;
        boundsTwo.height -= insets.top + insets.bottom;
        return boundsTwo;
    }

    public Rectangle GetBounds()
    {
        return this.bounds;
    }

    public void OpenUI()
    {

    }

}
