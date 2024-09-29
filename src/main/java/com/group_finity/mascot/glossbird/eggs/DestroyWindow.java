package com.group_finity.mascot.glossbird.eggs;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.environment.Area;
import com.group_finity.mascot.environment.home.BackgroundPanel;
import com.group_finity.mascot.environment.home.UI.CustomTitlebar;
import com.group_finity.mascot.win.WindowsUtil;
import com.sun.jna.platform.win32.WinDef;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.group_finity.mascot.Main.IMAGE_DIRECTORY;

public class DestroyWindow {
    public static DestroyWindow instance = new DestroyWindow();

    boolean isOpen;
    boolean closeOnEnd;
    JFrame testFrame;

    long frameId;

    public DestroyWindow(){
        closeOnEnd = false;
    }

    public static DestroyWindow getInstance() {
        return instance;
    }

    public void OpenWindow(long id)
    {
        frameId = id;
        if(isOpen)
        {
            CloseWindow();
        }

            try {
                TransparentOverlay();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    public void CloseWindow()
    {
        if(closeOnEnd)
        {
            //Close the active window when over
        }
        else
        {
            testFrame.dispose();
            isOpen = false;
        }
    }

    public void Tick()
    {
        if(isOpen)
        {
            final boolean isSameWindow = frameId == Main.getInstance().getMainMascot().getEnvironment().getActiveWindowId();

            if(isSameWindow && Main.getInstance().getMainMascot().getBehavior().toString().contains("Destroy"))
            {

                if(activeWindow!= null)
                {
                    testFrame.setLocation(activeWindow.toRectangle().getLocation());
                }
            }
            else
            {
                CloseWindow();
            }
        }
    }

    Area activeWindow;

    void TransparentOverlay() throws IOException {
        if(testFrame!= null)
        {
            testFrame.dispose();
            activeWindow = null;
        }
        testFrame = new JFrame();
        testFrame.setUndecorated(true);
        BufferedImage overlay = ImageIO.read(new File(IMAGE_DIRECTORY.toString(), Main.getInstance().getMainMascot().getImageSet() + "/ui/Destroy_overlay.png"));

        BackgroundPanel panel = new BackgroundPanel(overlay, BackgroundPanel.SCALED,0,0);
       // panel.setTransparentAdd(false);
        activeWindow = Main.getInstance().getMainMascot().getEnvironment().getActiveIE();

        testFrame.setSize(activeWindow.toRectangle().getSize());
        panel.setOpaque(false);
        testFrame.getContentPane().setBackground(new Color(0,0,0,0f));
        testFrame.setBackground(new Color(0,0,0,0f));
        testFrame.add(panel);
        JLabel testLabel = new JLabel("TESTINGSDUJGBNSD");
        panel.add(testLabel);
        testFrame.setLocation(activeWindow.toRectangle().getLocation());
        testFrame.setBounds(activeWindow.toRectangle());
        testFrame.setFocusable(false);
        testFrame.setFocusableWindowState(false);

        testFrame.setAlwaysOnTop(true);
        testFrame.setVisible(true);
        Main.getInstance().getMainMascot().RefreshOnTop();

        //Main.getInstance().getMainMascot().getWindow().setAlwaysOnTop(true);
        isOpen=  true;
    }

}
