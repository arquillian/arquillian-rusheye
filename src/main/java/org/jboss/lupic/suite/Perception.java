package org.jboss.lupic.suite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Perception", propOrder = { "onePixelTreshold", "globalDifferenceTreshold",
    "globalDifferencePixelAmount" })
public class Perception {

    Pattern percentPattern = Pattern.compile("([0-9]{1,2}|100)%");
    Pattern pixelPattern = Pattern.compile("(\\d)+px");
    
    @XmlElement(name = "one-pixel-treshold")
    protected Integer onePixelTreshold;
    @XmlElement(name = "global-difference-treshold")
    protected Integer globalDifferenceTreshold;
    @XmlElement(name = "global-difference-pixel-amount")
    protected String globalDifferenceAmount;

    public Integer getOnePixelTreshold() {
        return onePixelTreshold;
    }

    public void setOnePixelTreshold(Integer value) {
        this.onePixelTreshold = value;
    }

    public Integer getGlobalDifferenceTreshold() {
        return globalDifferenceTreshold;
    }

    public void setGlobalDifferenceTreshold(Integer value) {
        this.globalDifferenceTreshold = value;
    }

    public String getGlobalDifferenceAmount() {
        return globalDifferenceAmount;
    }

    public void setGlobalDifferenceAmount(String value) {
        this.globalDifferenceAmount = value;
    }
    
    /*
     * 
     */
    public Long getGlobalDifferencePixelAmount() {
        return getGlobalDifferenceAmount(AmountType.PIXEL).longValue();
    }

    public void setGlobalDifferencePixelAmount(long globalDifferencePixelAmount) {
        this.globalDifferenceAmount = Long.toString(globalDifferencePixelAmount) + "px";
    }

    public Short getGlobalDifferencePercentage() {
        return getGlobalDifferenceAmount(AmountType.PERCENTAGE).shortValue();
    }

    public void setGlobalDifferencePercentage(short globalDifferencePercentage) {
        this.globalDifferenceAmount = Short.valueOf(globalDifferencePercentage) + "%";
    }
    
    private enum AmountType {
        PERCENTAGE, PIXEL
    }
    
    private Number getGlobalDifferenceAmount(AmountType type) {
        Matcher matcher;
        for (Pattern pattern : new Pattern[] { percentPattern, pixelPattern }) {
            matcher = pattern.matcher(globalDifferenceAmount);
            if (matcher.lookingAt()) {
                if (pattern == percentPattern) {
                    return Short.valueOf(matcher.group(1));
                } else {
                    return Long.valueOf(matcher.group(1));
                }
            }
        }
        throw new IllegalStateException();
    }
}
