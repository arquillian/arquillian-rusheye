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

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @version $Revision$
 */
public class ComparisonResult {
    private boolean equalsImages;
    private BufferedImage diffImage;
    private Point rectangleMin;
    private Point rectangleMax;
    private int areaWidth;
    private int areaHeight;
    private int totalPixels;
    private int maskedPixels;
    private int perceptibleDiffs;
    private int differentPixels;
    private int smallDifferences;
    private int equalPixels;
    private String testName;

    public ComparisonResult(String testName, boolean equalsImages, BufferedImage diffImage, Point rectangleMin,
        Point rectangleMax, int areaWidth, int areaHeight, int totalPixels, int maskedPixels, int perceptibleDiffs,
        int differentPixels, int smallDifferences, int equalPixels) {
        super();
        this.testName = testName;
        this.equalsImages = equalsImages;
        this.diffImage = diffImage;
        this.rectangleMin = rectangleMin;
        this.rectangleMax = rectangleMax;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.totalPixels = totalPixels;
        this.maskedPixels = maskedPixels;
        this.perceptibleDiffs = perceptibleDiffs;
        this.differentPixels = differentPixels;
        this.smallDifferences = smallDifferences;
        this.equalPixels = equalPixels;
    }

    public boolean isEqualsImages() {
        return equalsImages;
    }

    public BufferedImage getDiffImage() {
        return diffImage;
    }

    public Point getRectangleMin() {
        return rectangleMin;
    }

    public Point getRectangleMax() {
        return rectangleMax;
    }

    public int getAreaWidth() {
        return areaWidth;
    }

    public int getAreaHeight() {
        return areaHeight;
    }

    public int getTotalPixels() {
        return totalPixels;
    }

    public int getMaskedPixels() {
        return maskedPixels;
    }

    public int getPerceptibleDiffs() {
        return perceptibleDiffs;
    }

    public int getDifferentPixels() {
        return differentPixels;
    }

    public int getSmallDifferences() {
        return smallDifferences;
    }

    public int getEqualPixels() {
        return equalPixels;
    }

    public String getTestName() {
        return testName;
    }

    public String getComparisonStatus() {
        return this.isEqualsImages() ? "same" : "different";
    }

    public String getAreaAsString() {
        return String.format("%d&times;%d", this.getAreaWidth(), this.getAreaHeight());
    }

    public String getRectangleAsString() {
        return String.format("[%d, %d] - [%d, %d]", this.getRectangleMin().x, this.getRectangleMin().y,
            this.getRectangleMax().x, this.getRectangleMax().y);
    }
}
