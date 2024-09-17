package com.group_finity.mascot.environment.home;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.environment.home.UI.AlarmPanel;
import com.group_finity.mascot.environment.home.UI.CustomTitlebar;
import com.group_finity.mascot.glossbird.Screenshot;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
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
    JFrame frame;
    public HomeUI() throws IOException {
        super();
         frame =  new CustomTitlebar();

        // frame.setUndecorated(true);
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
        BufferedImage homeImg = ImageIO.read(new File(IMAGE_DIRECTORY.toString(),  "House_Menu.png"));
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
        frame.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                // Set the location
                // get the current location x-co-ordinate and then get
                // the current drag x co-ordinate, add them and subtract most recent
                // mouse pressed x co-ordinate
                // do same for y co-ordinate
                loc = frame.getLocation();
            }
        });
        CustomTitlebar customBar ;
        BackgroundPanel backPanel = new BackgroundPanel(homeImg, BackgroundPanel.SCALED,0,0);
        backPanel.setTransparentAdd(false);
        GridBagConstraints c = new GridBagConstraints();

        backPanel.setLayout(null);

        JButton door = new JButton();
        //door.setOpaque(true);
        door.setContentAreaFilled(false);
        door.setBorderPainted(true);
        door.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.black));
        backPanel.add(door);
        backPanel.add(homeLabel);
        backPanel.add(alarmButton);
        backPanel.add(selfie);
        alarmButton.setBounds(80,160,60,60);
        selfie.setBounds(255,160,60,60);
        door.setBounds(168, 250, 75, 140);
        door.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getManager().setBehaviorAll("GoHome");
            }
        });

        backPanel.add(trinkets);

        contentPane.add(backPanel);
        backPanel.setOpaque(false);
        frame.getContentPane().setBackground(new Color(0,0,0,0f));
        frame.setBackground(new Color(0,0,0,0f));

        //contentPane.add(panel);

        Rectangle boundsTwo = getMaxWindowBounds(frame);

        bounds = new Rectangle(100, 100, 400, 400);
        frame.setBounds(bounds);
        loc = new Point((boundsTwo.x + boundsTwo.width - frame.getWidth()),boundsTwo.y + boundsTwo.height - frame.getHeight());
        frame.setLocation(loc);
        //frame.setUndecorated(true);
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

    public Point  GetDoorPosition()
    {
        Point test =  new Point();
        test.x = (int) frame.getBounds().getCenterX();
        test.y = 0;
        System.out.println("Home loc is " + test);
        return test;
    }


}
