package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.environment.Border;
import com.group_finity.mascot.environment.home.HomeUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.group_finity.mascot.Main.IMAGE_DIRECTORY;

public class Screenshot {

    public JFrame notif;
    public JFrame preview;
    public BufferedImage thumb;
    public BufferedImage combined;
    public BufferedImage capture;
    boolean showFriendInSelfie = false;
    HomeUI home;
    public Screenshot(HomeUI home)
    {
        super();
        this.home = home;
    }
    public static BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public void MakeThumbnail(BufferedImage image)
    {
        if(thumb != null)
            thumb.flush();
        thumb = convertToBufferedImage(image.getScaledInstance(200, 113, Image.SCALE_SMOOTH));

    }

    public void OpenPreview()
    {
        preview = new JFrame("Image Preview");
        JPanel panel = new JPanel(new BorderLayout());
        JLabel picLabel = new JLabel();
        if(showFriendInSelfie)
        {
            picLabel.setIcon(new ImageIcon(combined));
        }
        else
        {
            picLabel.setIcon(new ImageIcon(capture));
        }
        JScrollPane scrollPane = new JScrollPane(picLabel);
        JPanel insidePanel = new JPanel(new BorderLayout());
        insidePanel.add(scrollPane);
        panel.add(insidePanel, BorderLayout.CENTER);

        preview.add(panel);
        preview.pack();
        preview.setSize(HomeUI.getMaxWindowBounds(preview).getSize());
        preview.setLocation(0,0);
        preview.setVisible(true);
    }


    public void OpenScreenshotNotif()
    {
        notif = new JFrame("Selfie!");

        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);
        Point pos = home.GetHomePosition();
        notif.setSize(350,275);
        notif.setLocation(pos);

        JButton picLabel = new JButton(new ImageIcon(thumb));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;
        panel.add(picLabel,c);
        c.gridy = 1;
        JLabel textLabel = new JLabel("Wow! Cool selfie!");
        panel.add(textLabel,c);
        c.gridy =2;

        JButton openButton = new JButton("Save picture");
        panel.add(openButton,c);
        JCheckBox showPal = new JCheckBox("Show pal");
        showFriendInSelfie = true;
        showPal.setSelected(showFriendInSelfie);
        c.gridy = 4;
        showPal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPal.isSelected())
                {
                    showFriendInSelfie = true;
                    MakeThumbnail(combined);
                }
                else
                {
                    showFriendInSelfie = false;
                    MakeThumbnail(capture);
                }

                picLabel.setIcon(new ImageIcon(thumb));
            }
        });
        panel.add(showPal,c);
        openButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                try {
                    SaveImage();
                    Desktop.getDesktop().open( new File( "screenshot.png" ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        picLabel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                OpenPreview();

            }
        });
        notif.getContentPane().add(panel);
        notif.setVisible(true);
    }

    public void SaveImage()
    {

        try {
            if(showFriendInSelfie)
            {
                ImageIO.write(combined, "png", new File("screenshot.png"));
            }
            else
            {
                ImageIO.write(capture, "png", new File("screenshot.png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void MakeScreenshot() throws IOException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        capture = null;
        try {
            capture = new Robot().createScreenCapture(screenRect);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        BufferedImage overlay = ImageIO.read(new File(IMAGE_DIRECTORY.toString(), Main.getInstance().getMainMascot().getImageSet() + "/ui/Selfie_overlay.png"));
        BufferedImage scaled = convertToBufferedImage(overlay.getScaledInstance(screenRect.width, screenRect.height, Image.SCALE_SMOOTH));
        if(combined  != null)
            combined.flush();
        combined = new BufferedImage(screenRect.width, screenRect.height, BufferedImage.TYPE_INT_ARGB);


        Graphics g = combined.createGraphics();

        g.drawImage(capture,0,0,null);
        g.drawImage(scaled,0,0,null);

        MakeThumbnail(combined);

        try {
            ImageIO.write(thumb, "png", new File("thumb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OpenScreenshotNotif();
    }


}
