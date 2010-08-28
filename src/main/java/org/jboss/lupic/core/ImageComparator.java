/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.lupic.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageComparator {
    private void updateBoundary(Point min, Point max, int x, int y) {
        min.x = Math.min(min.x, x);
        min.y = Math.min(min.y, y);
        max.x = Math.max(max.x, x);
        max.y = Math.max(max.y, y);
    }

    private void drawRectangleAroundDifferentPixels(Configuration configuration, Point min, Point max, int width,
        int height, BufferedImage diffImage) {
        int x1 = Math.max(0, min.x - configuration.getBoundarySize());
        int y1 = Math.max(0, min.y - configuration.getBoundarySize());
        int x2 = Math.min(width - 1, max.x + configuration.getBoundarySize());
        int y2 = Math.min(height - 1, max.y + configuration.getBoundarySize());
        Graphics g = diffImage.createGraphics();
        g.setColor(configuration.getBoundaryColor());
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
        g.dispose();
    }

    private boolean isMaskedPixel(BufferedImage image, List<MaskImage> maskImages, int x, int y) {
        for (MaskImage maskImage : maskImages) {
            if (maskImage.isPixelMasked(image, x, y)) {
                return true;
            }
        }
        return false;
    }

    private Color getMaskedPixelColor(Color color) {
        int blue = (int) Math.round(color.getBlue() * 0.8);
        int green = Math.min(0xff, (int) Math.round(color.getGreen() * 1.2));
        int red = (int) Math.round(color.getRed() * 0.8);
        return new Color(red, green, blue);
    }

    public ComparisonResult diffImages(String imageFileName, BufferedImage[] images, List<MaskImage> maskImages,
        Configuration configuration) {
        Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        int width = Math.min(images[0].getWidth(), images[1].getWidth());
        int height = Math.min(images[0].getHeight(), images[1].getHeight());
        int totalPixels = 0;
        int maskedPixels = 0;
        int perceptibleDiffs = 0;
        int differentPixels = 0;
        int smallDifferences = 0;
        int equalPixels = 0;
        BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                totalPixels++;
                int cmp = ColorModelRGBA.compare(images[0].getRGB(i, j), images[1].getRGB(i, j));
                Color color = ColorModelRGBA.rgb2grayscale(images[0].getRGB(i, j));
                if (configuration.isUseMaskImages() && isMaskedPixel(images[0], maskImages, i, j)) {
                    maskedPixels++;
                    color = getMaskedPixelColor(color);
                } else if (cmp > configuration.getPerceptiblePixelValueThreshold()) {
                    perceptibleDiffs++;
                    updateBoundary(min, max, i, j);
                    color = configuration.getDiffColorPerceptiblePixelDifference();
                } else if (cmp > configuration.getDifferentPixelsThreshold()) {
                    differentPixels++;
                    updateBoundary(min, max, i, j);
                    color = configuration.getDiffColorPixelValueAboveThreshold();
                } else if (cmp > 0) {
                    smallDifferences++;
                    updateBoundary(min, max, i, j);
                    color = configuration.getDiffColorPixelValueUnderThreshold();
                } else {
                    equalPixels++;
                }
                diffImage.setRGB(i, j, color.getRGB());
            }
        }
        boolean equalImages = min.x == Integer.MAX_VALUE;
        if (!equalImages) {
            drawRectangleAroundDifferentPixels(configuration, min, max, width, height, diffImage);
        } else {
            min = new Point(-1, -1);
            max = new Point(-1, -1);
        }
        return new ComparisonResult(imageFileName, equalImages, diffImage, min, max, width, height, totalPixels,
            maskedPixels, perceptibleDiffs, differentPixels, smallDifferences, equalPixels);
    }

}
