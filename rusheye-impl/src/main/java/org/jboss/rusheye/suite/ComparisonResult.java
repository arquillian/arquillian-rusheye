/**
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
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
package org.jboss.rusheye.suite;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "area", "rectangles", "totalPixels", "maskedPixels", "perceptibleDiffs", "differentPixels",
        "smallDifferences", "equalPixels" })
public class ComparisonResult {
    private boolean equalsImages;
    private BufferedImage diffImage;
    private List<Rectangle> rectangles;
    private Area area = new Area();
    private int totalPixels;
    private int maskedPixels;
    private int perceptibleDiffs;
    private int differentPixels;
    private int smallDifferences;
    private int equalPixels;

    public ComparisonResult() {
    }

    public ComparisonResult(boolean equalsImages, BufferedImage diffImage, Point rectangleMin, Point rectangleMax,
        int areaWidth, int areaHeight, int totalPixels, int maskedPixels, int perceptibleDiffs, int differentPixels,
        int smallDifferences, int equalPixels) {
        this.equalsImages = equalsImages;
        this.diffImage = diffImage;
        this.area.setWidth(areaWidth);
        this.area.setHeight(areaHeight);
        this.totalPixels = totalPixels;
        this.maskedPixels = maskedPixels;
        this.perceptibleDiffs = perceptibleDiffs;
        this.differentPixels = differentPixels;
        this.smallDifferences = smallDifferences;
        this.equalPixels = equalPixels;
        if (rectangleMax.getX() > 0 && rectangleMax.getY() > 0) {
            Rectangle rectangle = new Rectangle();
            this.getRectangles().add(rectangle);
            rectangle.setMin(rectangleMin);
            rectangle.setMax(rectangleMax);
        }
    }

    @XmlTransient
    public boolean isEqualsImages() {
        return equalsImages;
    }

    public void setEqualsImages(boolean equalsImages) {
        this.equalsImages = equalsImages;
    }

    @XmlTransient
    public BufferedImage getDiffImage() {
        return diffImage;
    }

    public void setDiffImage(BufferedImage diffImage) {
        this.diffImage = diffImage;
    }

    @XmlElement(name = "rectangle")
    public List<Rectangle> getRectangles() {
        if (rectangles == null) {
            rectangles = new LinkedList<Rectangle>();
        }
        return rectangles;
    }

    @XmlElement
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @XmlElement(name = "total-pixels")
    public int getTotalPixels() {
        return totalPixels;
    }

    public void setTotalPixels(int totalPixels) {
        this.totalPixels = totalPixels;
    }

    @XmlElement(name = "masked-pixels")
    public int getMaskedPixels() {
        return maskedPixels;
    }

    public void setMaskedPixels(int maskedPixels) {
        this.maskedPixels = maskedPixels;
    }

    @XmlElement(name = "perceptible-different-pixels")
    public int getPerceptibleDiffs() {
        return perceptibleDiffs;
    }

    public void setPerceptibleDiffs(int perceptibleDiffs) {
        this.perceptibleDiffs = perceptibleDiffs;
    }

    @XmlElement(name = "global-different-pixels")
    public int getDifferentPixels() {
        return differentPixels;
    }

    public void setDifferentPixels(int differentPixels) {
        this.differentPixels = differentPixels;
    }

    @XmlElement(name = "unperceptible-different-pixels")
    public int getSmallDifferences() {
        return smallDifferences;
    }

    public void setSmallDifferences(int smallDifferences) {
        this.smallDifferences = smallDifferences;
    }

    @XmlElement(name = "same-pixels")
    public int getEqualPixels() {
        return equalPixels;
    }

    public void setEqualPixels(int equalPixels) {
        this.equalPixels = equalPixels;
    }

    public String getComparisonStatus() {
        return this.isEqualsImages() ? "same" : "different";
    }
}
