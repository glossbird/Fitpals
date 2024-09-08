package com.group_finity.mascot.environment.home;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.environment.home.UI.AlarmPanel;
import com.group_finity.mascot.glossbird.Screenshot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.group_finity.mascot.Main.IMAGE_DIRECTORY;

public class HomeUI {

    Rectangle bounds;
    HomeArea areaLink;
    AlarmPanel alarmPanel;
    Screenshot screenshot;
    Point loc;

    public HomeUI() throws IOException {
        super();
        JFrame frame = new JFrame("Home");
        Container contentPane = frame.getContentPane();
      //  contentPane.setLayout(new FlowLayout());


        JTextField textField = new JTextField(50);
        JLabel homeLabel = new JLabel("Home");
        JButton alarmButton = new JButton("Alarm");
        JButton trinkets = new JButton("Trinkets");
        JButton selfie = new JButton();
        BufferedImage selfieImg = ImageIO.read(new File(IMAGE_DIRECTORY.toString(), Main.getInstance().getMainMascot().getImageSet() + "/ui/selfie.png"));
        selfie.setIcon(new ImageIcon(selfieImg));
        JLabel homeImage = new JLabel();
        BufferedImage homeImg = ImageIO.read(new File(IMAGE_DIRECTORY.toString(),  "home.jpg"));
        homeImage.setIcon(new ImageIcon(homeImg));
        alarmButton.setSize(200,100);
        selfie.setSize(100,100);
        screenshot = new Screenshot(this);
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
        alarmPanel = new AlarmPanel(Main.getInstance().getAlarmManager());
        alarmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                alarmPanel.OpenPanel();

            }

        });

        JPanel panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(homeImg, 0, 0, null);
            }
        };
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        BackgroundPanel backPanel = new BackgroundPanel(homeImg, BackgroundPanel.SCALED,0,0);
        backPanel.setTransparentAdd(false);
        GridBagConstraints c = new GridBagConstraints();

        backPanel.setLayout(null);
        backPanel.add(homeLabel);
        backPanel.add(alarmButton);
        backPanel.add(selfie);
        alarmButton.setBounds(80,175,60,60);
        selfie.setBounds(255,175,60,60);
        backPanel.add(trinkets);

        frame.add(backPanel);
        //contentPane.add(panel);

        Rectangle boundsTwo = getMaxWindowBounds(frame);

        bounds = new Rectangle(100, 100, 400, 400);
        frame.setBounds(bounds);
        loc = new Point((boundsTwo.x + boundsTwo.width - frame.getWidth()),boundsTwo.y + boundsTwo.height - frame.getHeight());
        frame.setLocation(loc);
        frame.setUndecorated(true);
        panel.setLocation(frame.getBounds().getLocation());
        panel.setSize(frame.getSize());



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

    public Point GetHomePosition()
    {
        return loc;
    }


}
