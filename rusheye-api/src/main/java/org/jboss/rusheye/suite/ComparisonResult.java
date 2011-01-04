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

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Result of the comparison containing details of the comparison process.
 * 
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "area", "rectangles", "totalPixels", "maskedPixels", "perceptibleDiffs", "differentPixels",
        "smallDifferences", "equalPixels" })
public class ComparisonResult {

    /** The equals images. */
    private boolean equalsImages;

    /** The diff image. */
    private BufferedImage diffImage;

    /** The rectangles. */
    private List<Rectangle> rectangles;

    /** The area. */
    private Area area = new Area();

    /** The total pixels. */
    private int totalPixels;

    /** The masked pixels. */
    private int maskedPixels;

    /** The perceptible diffs. */
    private int perceptibleDiffs;

    /** The different pixels. */
    private int differentPixels;

    /** The small differences. */
    private int smallDifferences;

    /** The equal pixels. */
    private int equalPixels;

    /**
     * Checks if is equals images.
     * 
     * @return true, if is equals images
     */
    @XmlTransient
    public boolean isEqualsImages() {
        return equalsImages;
    }

    /**
     * Sets the equals images.
     * 
     * @param equalsImages
     *            the new equals images
     */
    public void setEqualsImages(boolean equalsImages) {
        this.equalsImages = equalsImages;
    }

    /**
     * Gets the diff image.
     * 
     * @return the diff image
     */
    @XmlTransient
    public BufferedImage getDiffImage() {
        return diffImage;
    }

    /**
     * Sets the diff image.
     * 
     * @param diffImage
     *            the new diff image
     */
    public void setDiffImage(BufferedImage diffImage) {
        this.diffImage = diffImage;
    }

    /**
     * <p>
     * Gets the rectangles.
     * </p>
     * 
     * <p>
     * During first invocation of this method, new empty list of rectangles is created.
     * </p>
     * 
     * @return the rectangles
     */
    @XmlElement(name = "rectangle")
    public List<Rectangle> getRectangles() {
        if (rectangles == null) {
            rectangles = new LinkedList<Rectangle>();
        }
        return rectangles;
    }

    /**
     * Gets the area.
     * 
     * @return the area
     */
    @XmlElement
    public Area getArea() {
        return area;
    }

    /**
     * Sets the area.
     * 
     * @param area
     *            the new area
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * Gets the total pixels.
     * 
     * @return the total pixels
     */
    @XmlElement(name = "total-pixels")
    public int getTotalPixels() {
        return totalPixels;
    }

    /**
     * Sets the total pixels.
     * 
     * @param totalPixels
     *            the new total pixels
     */
    public void setTotalPixels(int totalPixels) {
        this.totalPixels = totalPixels;
    }

    /**
     * Gets the masked pixels.
     * 
     * @return the masked pixels
     */
    @XmlElement(name = "masked-pixels")
    public int getMaskedPixels() {
        return maskedPixels;
    }

    /**
     * Sets the masked pixels.
     * 
     * @param maskedPixels
     *            the new masked pixels
     */
    public void setMaskedPixels(int maskedPixels) {
        this.maskedPixels = maskedPixels;
    }

    /**
     * Gets the perceptible diffs.
     * 
     * @return the perceptible diffs
     */
    @XmlElement(name = "perceptible-different-pixels")
    public int getPerceptibleDiffs() {
        return perceptibleDiffs;
    }

    /**
     * Sets the perceptible diffs.
     * 
     * @param perceptibleDiffs
     *            the new perceptible diffs
     */
    public void setPerceptibleDiffs(int perceptibleDiffs) {
        this.perceptibleDiffs = perceptibleDiffs;
    }

    /**
     * Gets the different pixels.
     * 
     * @return the different pixels
     */
    @XmlElement(name = "global-different-pixels")
    public int getDifferentPixels() {
        return differentPixels;
    }

    /**
     * Sets the different pixels.
     * 
     * @param differentPixels
     *            the new different pixels
     */
    public void setDifferentPixels(int differentPixels) {
        this.differentPixels = differentPixels;
    }

    /**
     * Gets the small differences.
     * 
     * @return the small differences
     */
    @XmlElement(name = "unperceptible-different-pixels")
    public int getSmallDifferences() {
        return smallDifferences;
    }

    /**
     * Sets the small differences.
     * 
     * @param smallDifferences
     *            the new small differences
     */
    public void setSmallDifferences(int smallDifferences) {
        this.smallDifferences = smallDifferences;
    }

    /**
     * Gets the equal pixels.
     * 
     * @return the equal pixels
     */
    @XmlElement(name = "same-pixels")
    public int getEqualPixels() {
        return equalPixels;
    }

    /**
     * Sets the equal pixels.
     * 
     * @param equalPixels
     *            the new equal pixels
     */
    public void setEqualPixels(int equalPixels) {
        this.equalPixels = equalPixels;
    }

    /**
     * Gets the comparison status.
     * 
     * @return the comparison status
     */
    public String getComparisonStatus() {
        return this.isEqualsImages() ? "same" : "different";
    }
    
    @Override
    public String toString() {
        return "ComparisonResult [equalsImages=" + equalsImages + ", totalPixels=" + totalPixels + ", maskedPixels="
            + maskedPixels + ", perceptibleDiffs=" + perceptibleDiffs + ", differentPixels=" + differentPixels
            + ", smallDifferences=" + smallDifferences + ", equalPixels=" + equalPixels + "]";
    }
}
