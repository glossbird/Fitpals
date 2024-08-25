package com.group_finity.mascot.glossbird;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.group_finity.mascot.Main.IMAGE_DIRECTORY;

public class Screenshot {


    public Screenshot()
    {
        super();
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

    public void MakeScreenshot() throws IOException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = null;
        try {
            capture = new Robot().createScreenCapture(screenRect);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        BufferedImage overlay = ImageIO.read(new File(IMAGE_DIRECTORY.toString(), "ShimejiMonroe/Selfie_overlay.png"));
        BufferedImage scaled = convertToBufferedImage(overlay.getScaledInstance(screenRect.width, screenRect.height, Image.SCALE_SMOOTH));
        BufferedImage combined = new BufferedImage(screenRect.width, screenRect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.createGraphics();
        g.drawImage(capture,0,0,null);
        g.drawImage(scaled,0,0,null);

        try {
            ImageIO.write(combined, "png", new File("screenshot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
