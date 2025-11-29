package frontend.components;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconLoader {
    public static ImageIcon loadIcon(String filePath)
    {
        InputStream inputStream = IconLoader.class.getResourceAsStream(filePath);
        if (inputStream == null) {
            System.err.println("Icon not found: " + filePath);
            return null;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return new ImageIcon(bufferedImage);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
