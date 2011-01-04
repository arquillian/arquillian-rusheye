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
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.rusheye.retriever.MaskRetriever;

/**
 * <p>
 * The mask for the filtering of the samples during comparison.
 * </p>
 * 
 * <p>
 * Required options are type, source and id.
 * </p>
 * 
 * <p>
 * Optional settings are alignment of the mask (for types which requires this settings).
 * </p>
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Mask")
public class Mask extends ImageSource {

    private static final int ALPHA_MASK = 0xFF000000;
    
    /** The id. */
    protected String id;
    
    /** The type. */
    protected MaskType type;
    
    /** The vertical align. */
    protected VerticalAlign verticalAlign;
    
    /** The horizontal align. */
    protected HorizontalAlign horizontalAlign;

    /** The mask retriever. */
    @Resource
    @XmlTransient
    private MaskRetriever maskRetriever;
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param value the new id
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @XmlAttribute(required = true)
    public MaskType getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param value the new type
     */
    public void setType(MaskType value) {
        this.type = value;
    }

    /**
     * Gets the vertical alignment.
     *
     * @return the vertical alignment
     */
    @XmlAttribute(name = "vertical-align")
    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    /**
     * Sets the vertical alignment.
     *
     * @param value the new vertical alignment
     */
    public void setVerticalAlign(VerticalAlign value) {
        this.verticalAlign = value;
    }

    /**
     * Gets the horizontal alignment.
     *
     * @return the horizontal alignment
     */
    @XmlAttribute(name = "horizontal-align")
    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    /**
     * Sets the horizontal alignment.
     *
     * @param value the new horizontal alignment
     */
    public void setHorizontalAlign(HorizontalAlign value) {
        this.horizontalAlign = value;
    }
    
    /* (non-Javadoc)
     * @see org.jboss.rusheye.suite.ImageSource#retrieve()
     */
    @Override
    public BufferedImage retrieve() throws Exception {
        return maskRetriever.retrieve(source, this);
    }

    /**
     * Gets the mask.
     *
     * @return the mask
     */
    private BufferedImage getMaskImage() {
        run();
        try {
            return get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if is pixel masked.
     *
     * @param pattern the pattern
     * @param x the x
     * @param y the y
     * @return true, if is pixel masked
     */
    public boolean isPixelMasked(BufferedImage pattern, int x, int y) {
        final BufferedImage maskImage = getMaskImage();

        int patternWidth = pattern.getWidth();
        int patternHeight = pattern.getHeight();
        int maskWidth = maskImage.getWidth();
        int maskHeight = maskImage.getHeight();

        int maskX = this.horizontalAlign == HorizontalAlign.LEFT ? x : x - (patternWidth - maskWidth);
        int maskY = this.verticalAlign == VerticalAlign.TOP ? y : y - (patternHeight - maskHeight);

        // we are outside the mask
        if (maskX < 0 || maskX >= maskWidth || maskY < 0 || maskY >= maskHeight) {
            return false;
        }

        return (maskImage.getRGB(maskX, maskY) & ALPHA_MASK) != 0;
    }

    /**
     * Sets the mask retriever.
     *
     * @param maskRetriever the new mask retriever
     */
    public void setMaskRetriever(MaskRetriever maskRetriever) {
        this.maskRetriever = maskRetriever;
    }
}
