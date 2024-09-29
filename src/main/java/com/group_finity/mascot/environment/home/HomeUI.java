package com.group_finity.mascot.environment.home;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.Manager;
import com.group_finity.mascot.environment.home.UI.AlarmPanel;
import com.group_finity.mascot.environment.home.UI.CustomTitlebar;
import com.group_finity.mascot.environment.home.UI.TimerPanel;
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

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JFrame DebugGraphicPanel()
    {
        JFrame testPane = new JFrame();
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
        testPane.setSize(300,400);
        BufferedImage selfieImg;
        try {
            selfieImg = ImageIO.read(new File(IMAGE_DIRECTORY.toString(), "/testimage.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JPanel panelOne = new JPanel(new FlowLayout());
        JPanel panelTwo = new JPanel(new FlowLayout());
        JPanel panelThree = new JPanel(new FlowLayout());
        JPanel panelFour = new JPanel(new FlowLayout());

        JLabel icon = new JLabel(new ImageIcon(selfieImg));
        JLabel oneText = new JLabel("1x");

        Image newimg = selfieImg.getScaledInstance(42, 38,  Image.SCALE_DEFAULT);

        JLabel iconTwo = new JLabel(new ImageIcon(newimg));
        iconTwo.setPreferredSize(new Dimension(42,38));
        JLabel twoText = new JLabel("2x");

         newimg = selfieImg.getScaledInstance(21*3, 19*3,  Image.SCALE_DEFAULT);
        JLabel iconThree = new JLabel(new ImageIcon(newimg));
        iconThree.setPreferredSize(new Dimension(21*3,19*3));
        iconThree.setMinimumSize(new Dimension(21*3,19*3));
        JLabel threeText = new JLabel("3x");

        newimg = selfieImg.getScaledInstance(21*6, 19*6,  Image.SCALE_DEFAULT);
        JLabel iconFour = new JLabel(new ImageIcon(newimg));
        iconFour.setPreferredSize(new Dimension(21*6,19*6));
        iconFour.setMinimumSize(new Dimension(21*6,19*6));
        JLabel fourText = new JLabel("6x");

        panelOne.add(icon);
        panelOne.add(oneText);

        panelTwo.add(iconTwo);
        panelTwo.add(twoText);

        panelThree.add(iconThree);
        panelThree.add(threeText);

        panelFour.add(iconFour);
        panelFour.add(fourText);

        testPanel.add(panelOne);
        testPanel.add(panelTwo);
        testPanel.add(panelThree);
        testPanel.add(panelFour);
        testPane.setLocation(loc);
        testPane.add(testPanel);
        return testPane;
    }

    public HomeUI() throws IOException {
        super();
         frame =  new CustomTitlebar();

        // frame.setUndecorated(true);
        Container contentPane = frame.getContentPane();
      //  contentPane.setLayout(new FlowLayout());


        JTextField textField = new JTextField(50);
        JLabel homeLabel = new JLabel("Home");
        JButton alarmButton = new JButton("Alarm");
        JButton timerButton = new JButton("Timer");
        JButton trinkets = new JButton("Trinkets");
        JButton destroy = new JButton("Destroy");
        JButton selfie = new JButton();
        BufferedImage selfieImg = ImageIO.read(new File(IMAGE_DIRECTORY.toString(), Main.getInstance().getMainMascot().getImageSet() + "/ui/selfie.png"));
        selfie.setIcon(new ImageIcon(selfieImg));
        JLabel homeImage = new JLabel();
        BufferedImage homeImg = ImageIO.read(new File(IMAGE_DIRECTORY.toString(),  "House_Menu.png"));
        homeImage.setIcon(new ImageIcon(homeImg));
        alarmButton.setSize(200,100);
        timerButton.setSize(200,100);

        selfie.setSize(100,100);
        destroy.setSize(100,100);
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
        destroy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getManager().setBehaviorAll("DestroyWindow");
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
        timerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                TimerPanel tp = new TimerPanel();



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
        backPanel.add(timerButton);
        backPanel.add(selfie);
        backPanel.add(destroy);
        alarmButton.setBounds(80,160,60,60);
        destroy.setBounds(168,160,60,60);
        timerButton.setBounds(80,280,60,60);
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

        JFrame test = DebugGraphicPanel();
        test.setVisible(true);
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
