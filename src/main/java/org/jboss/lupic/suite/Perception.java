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
        Number number = getGlobalDifferenceAmount(AmountType.PIXEL);
        return (number != null) ? number.longValue() : null;
    }

    public void setGlobalDifferencePixelAmount(long globalDifferencePixelAmount) {
        this.globalDifferenceAmount = Long.toString(globalDifferencePixelAmount) + "px";
    }

    public Short getGlobalDifferencePercentage() {
        Number number = getGlobalDifferenceAmount(AmountType.PERCENTAGE);
        return (number != null) ? number.shortValue() : null;
    }

    public void setGlobalDifferencePercentage(short globalDifferencePercentage) {
        this.globalDifferenceAmount = Short.valueOf(globalDifferencePercentage) + "%";
    }

    private enum AmountType {
        PERCENTAGE("([0-9]{1,2}|100)%"), PIXEL("(\\d)+px");

        private AmountType(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        Pattern pattern;

        Pattern getPattern() {
            return pattern;
        }

        Number parseAmount(String string) {
            if (this == PERCENTAGE) {
                return Short.valueOf(string);
            } else {
                return Long.valueOf(string);
            }
        }
    }

    private Number getGlobalDifferenceAmount(AmountType amountType) {
        if (globalDifferenceAmount == null) {
            return null;
        }

        Matcher matcher = amountType.getPattern().matcher(globalDifferenceAmount);
        if (matcher.lookingAt()) {
            return amountType.parseAmount(matcher.group(1));
        } else {
            return null;
        }
    }
}
