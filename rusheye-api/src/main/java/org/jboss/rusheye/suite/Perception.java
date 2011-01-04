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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The perceptual settings to be used in comparison process.
 * </p>
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Perception", propOrder = { "onePixelTreshold", "globalDifferenceTreshold", "globalDifferenceAmount" })
public class Perception {

    /**
     * Used in case to decide that {@link AmountType} which tries to obtain is not the current {@link AmountType} set in
     * this settings.
     */
    private static final Number NOT_THIS_TYPE = new Double("0");

    /** The one pixel treshold. */
    protected Float onePixelTreshold;

    /** The global difference treshold. */
    protected Float globalDifferenceTreshold;

    /** The global difference amount. */
    protected String globalDifferenceAmount;

    /**
     * Gets the one pixel treshold.
     * 
     * @return the one pixel treshold
     */
    @XmlElement(name = "one-pixel-treshold")
    public Float getOnePixelTreshold() {
        return onePixelTreshold;
    }

    /**
     * Sets the one pixel treshold.
     * 
     * @param value
     *            the new one pixel treshold
     */
    public void setOnePixelTreshold(Float value) {
        this.onePixelTreshold = value;
    }

    /**
     * Gets the global difference treshold.
     * 
     * @return the global difference treshold
     */
    @XmlElement(name = "global-difference-treshold")
    public Float getGlobalDifferenceTreshold() {
        return globalDifferenceTreshold;
    }

    /**
     * Sets the global difference treshold.
     * 
     * @param value
     *            the new global difference treshold
     */
    public void setGlobalDifferenceTreshold(Float value) {
        this.globalDifferenceTreshold = value;
    }

    /**
     * Gets the global difference amount.
     * 
     * @return the global difference amount
     */
    @XmlElement(name = "global-difference-pixel-amount")
    public String getGlobalDifferenceAmount() {
        return globalDifferenceAmount;
    }

    /**
     * Sets the global difference amount.
     * 
     * @param value
     *            the new global difference amount
     */
    public void setGlobalDifferenceAmount(String value) {
        this.globalDifferenceAmount = value;
    }

    /*
     * 
     */
    /**
     * Gets the global difference pixel amount.
     * 
     * @return the global difference pixel amount
     */
    @XmlTransient
    public Long getGlobalDifferencePixelAmount() {
        Number number = getGlobalDifferenceAmount(AmountType.PIXEL);
        return (number != NOT_THIS_TYPE) ? number.longValue() : null;
    }

    /**
     * Sets the global difference pixel amount.
     * 
     * @param globalDifferencePixelAmount
     *            the new global difference pixel amount
     */
    public void setGlobalDifferencePixelAmount(long globalDifferencePixelAmount) {
        this.globalDifferenceAmount = Long.toString(globalDifferencePixelAmount) + "px";
    }

    /**
     * Gets the global difference percentage.
     * 
     * @return the global difference percentage
     */
    @XmlTransient
    public Short getGlobalDifferencePercentage() {
        Number number = getGlobalDifferenceAmount(AmountType.PERCENTAGE);
        return (number != NOT_THIS_TYPE) ? number.shortValue() : null;
    }

    /**
     * Sets the global difference percentage.
     * 
     * @param globalDifferencePercentage
     *            the new global difference percentage
     */
    public void setGlobalDifferencePercentage(short globalDifferencePercentage) {
        this.globalDifferenceAmount = Short.valueOf(globalDifferencePercentage) + "%";
    }

    /**
     * Gets the global difference amount.
     * 
     * @param amountType
     *            the amount type
     * @return the global difference amount
     */
    public Number getGlobalDifferenceAmount(AmountType amountType) {
        String amount = getGlobalDifferenceAmount();
        if (amount == null) {
            return null;
        }

        Matcher matcher = amountType.getPattern().matcher(amount);
        if (matcher.lookingAt()) {
            return amountType.parseAmount(matcher.group(1));
        } else {
            return NOT_THIS_TYPE;
        }
    }

    public AmountType getGlobalDifferenceAmountType() {
        String amount = getGlobalDifferenceAmount();

        for (AmountType type : AmountType.values()) {
            if (type.getPattern().matcher(amount).matches()) {
                return type;
            }
        }

        throw new IllegalStateException("The amount needs to be one of AmountType");
    }

    /**
     * The enumeration of amount types.
     */
    public static enum AmountType {

        /** The PERCENTAGE. */
        PERCENTAGE("([0-9]{1,2}|100)%"),
        /** The PIXEL. */
        PIXEL("^([0-9]+)px$");

        /** The pattern. */
        private Pattern pattern;

        /**
         * Instantiates a new amount type.
         * 
         * @param pattern
         *            the pattern
         */
        private AmountType(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        /**
         * Gets the pattern.
         * 
         * @return the pattern
         */
        Pattern getPattern() {
            return pattern;
        }

        /**
         * Parses the amount.
         * 
         * @param string
         *            the string
         * @return the number
         */
        Number parseAmount(String string) {
            if (this == PERCENTAGE) {
                return Short.valueOf(string);
            } else {
                return Long.valueOf(string);
            }
        }
    }
}
