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
package org.jboss.rusheye.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Collection;

import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Perception;

/**
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @version $Revision$
 */
public class DefaultImageComparator implements ImageComparator {
    private static final int BOUNDARY_SIZE = 5;
    private static final Color BOUNDARY_COLOR = new Color(0, 128, 255);
    private static final Color DIFF_COLOR_UNDER_TRESSHOLD = new Color(0, 0, 255);
    private static final Color DIFF_COLOR_ABOVE_TRESHOLD = new Color(255, 0, 255);
    private static final Color DIFF_COLOR_PERCEPTIBLE = new Color(255, 0, 0);

    private void updateBoundary(Point min, Point max, int x, int y) {
        min.x = Math.min(min.x, x);
        min.y = Math.min(min.y, y);
        max.x = Math.max(max.x, x);
        max.y = Math.max(max.y, y);
    }

    private void drawRectangleAroundDifferentPixels(Point min, Point max, int width, int height, BufferedImage diffImage) {
        int x1 = Math.max(0, min.x - BOUNDARY_SIZE);
        int y1 = Math.max(0, min.y - BOUNDARY_SIZE);
        int x2 = Math.min(width - 1, max.x + BOUNDARY_SIZE);
        int y2 = Math.min(height - 1, max.y + BOUNDARY_SIZE);
        Graphics g = diffImage.createGraphics();
        g.setColor(BOUNDARY_COLOR);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
        g.dispose();
    }

    private boolean isMaskedPixel(BufferedImage image, Collection<Mask> masks, int x, int y) {
        for (Mask mask : masks) {
            if (mask.isPixelMasked(image, x, y)) {
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

    public ComparisonResult compare(BufferedImage patternImage, BufferedImage sampleImage, Perception perception,
        Collection<Mask> selectiveAlphaMasks) {
        Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        int width = Math.min(patternImage.getWidth(), sampleImage.getWidth());
        int height = Math.min(patternImage.getHeight(), sampleImage.getHeight());
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
                int cmp = ColorModelRGBA.compare(patternImage.getRGB(i, j), sampleImage.getRGB(i, j));
                Color color = ColorModelRGBA.rgb2grayscale(patternImage.getRGB(i, j));
                if (isMaskedPixel(patternImage, selectiveAlphaMasks, i, j)) {
                    maskedPixels++;
                    color = getMaskedPixelColor(color);
                } else if (cmp > perception.getOnePixelTreshold()) {
                    perceptibleDiffs++;
                    updateBoundary(min, max, i, j);
                    color = DIFF_COLOR_PERCEPTIBLE;
                } else if (cmp > perception.getGlobalDifferenceTreshold()) {
                    differentPixels++;
                    updateBoundary(min, max, i, j);
                    color = DIFF_COLOR_ABOVE_TRESHOLD;
                } else if (cmp > 0) {
                    smallDifferences++;
                    updateBoundary(min, max, i, j);
                    color = DIFF_COLOR_UNDER_TRESSHOLD;
                } else {
                    equalPixels++;
                }
                diffImage.setRGB(i, j, color.getRGB());
            }
        }
        boolean equalImages = min.x == Integer.MAX_VALUE;
        if (!equalImages) {
            drawRectangleAroundDifferentPixels(min, max, width, height, diffImage);
        } else {
            min = new Point(-1, -1);
            max = new Point(-1, -1);
        }
        return new ComparisonResult(equalImages, diffImage, min, max, width, height, totalPixels, maskedPixels,
            perceptibleDiffs, differentPixels, smallDifferences, equalPixels);
    }

}
