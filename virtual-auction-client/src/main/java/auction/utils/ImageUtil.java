package auction.utils;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class ImageUtil {
    
    public ImageIcon resizeIcon(ImageIcon icon, int maxWidth, int maxHeight) {
        Image img = icon.getImage();
        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();

        double aspectRatio = (double) originalWidth / originalHeight;

        int newWidth = maxWidth;
        int newHeight = maxHeight;

        if (originalWidth > originalHeight) {
            newHeight = (int) (maxWidth / aspectRatio);
        } else {
            newWidth = (int) (maxHeight * aspectRatio);
        }

        Image resizedImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public ImageIcon createImageIconAbsolute(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        File file = new File(path);
        if (file.exists()) {
            return new ImageIcon(file.getAbsolutePath());
        } else {
            return null;
        }
    }
}