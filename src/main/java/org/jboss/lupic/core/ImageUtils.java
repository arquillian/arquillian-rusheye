package org.jboss.lupic.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public final class ImageUtils {
    
    private ImageUtils() {
    }

    private static BufferedImage readImage(File directory, String fileName) throws IOException {
        File imageFile = new File(directory, fileName);
        return ImageIO.read(imageFile);
    }

    public static void writeImage(BufferedImage img, File directory, String fileName) throws IOException {
        ImageIO.write(img, "png", new File(directory, fileName));
    }

    public static BufferedImage[] readSourceImages(File firstDirectory, File secondDirectory, String fileName)
        throws Exception {
        BufferedImage[] images = new BufferedImage[2];
        for (int i = 0; i < 2; i++) {
            File directory = i == 0 ? firstDirectory : secondDirectory;
            try {
                images[i] = ImageUtils.readImage(directory, fileName);
                Log.logProcess("loading image %s", new File(directory, fileName).getAbsolutePath());
            } catch (IOException e) {
                throw new Exception("Cannot load or initialize image " + fileName);
            }
        }
        return images;
    }

    public static List<MaskImage> readMaskImages(File maskDirectory) throws Exception {
        List<MaskImage> images = new ArrayList<MaskImage>();

        for (String imageFileName : maskDirectory.list()) {
            Log.logSetup("read mask image %s", imageFileName);
            images.add(new MaskImage(maskDirectory.getAbsolutePath(), imageFileName));
        }
        return images;
    }

}
