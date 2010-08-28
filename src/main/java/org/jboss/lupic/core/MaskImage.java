package org.jboss.lupic.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class MaskImage {
    private BufferedImage mask;
    private HorizontalOrientation horizontalOrientation;
    private VerticalOrientation verticalOrientation;

    public MaskImage(String directory, String imageFileName) throws IOException {
        this.mask = readMaskImage(directory, imageFileName);
        this.horizontalOrientation = resolveHorizontalOrientation(imageFileName);
        this.verticalOrientation = resolveVerticalOrientation(imageFileName);
    }

    private HorizontalOrientation resolveHorizontalOrientation(String fileName) {
        return this.getFileFlags(fileName).contains("right") ? HorizontalOrientation.RIGHT : HorizontalOrientation.LEFT;
    }

    private VerticalOrientation resolveVerticalOrientation(String fileName) {
        return this.getFileFlags(fileName).contains("bottom") ? VerticalOrientation.BOTTOM : VerticalOrientation.TOP;
    }

    private List<String> getFileFlags(String fileName) {
        String[] parts = fileName.split("\\.");
        // middle parts is composed of file flags, for example "top-left", "bottom-right" etc.
        String[] flags = parts[1].split("-");
        return Arrays.asList(flags);
    }

    private BufferedImage readMaskImage(String directory, String fileName) throws IOException {
        File imageFile = new File(directory, fileName);
        // System.err.println(imageFile.getAbsolutePath());
        return ImageIO.read(imageFile);
    }

    public boolean isPixelMasked(BufferedImage sourceImage, int x, int y) {
        int sourceWidth = sourceImage.getWidth();
        int sourceHeight = sourceImage.getHeight();
        int maskWidth = this.mask.getWidth();
        int maskHeight = this.mask.getHeight();
        int mx = this.horizontalOrientation == HorizontalOrientation.LEFT ? x : x - (sourceWidth - maskWidth);
        int my = this.verticalOrientation == VerticalOrientation.TOP ? y : y - (sourceHeight - maskHeight);
        // we are outside the mask
        if (mx < 0 || mx >= maskWidth || my < 0 || my >= maskHeight) {
            return false;
        }
        return (this.mask.getRGB(mx, my) & 0xff000000) != 0;
    }
}
