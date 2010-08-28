package org.jboss.lupic.suite;

public class Perception {
    Short onePixelTreshold = null;
    Short globalDifferenceTreshold = null;
    Long globalDifferencePixelAmount = null;
    Short globalDifferencePercentage = null;

    public Perception() {
    }

    public Short getOnePixelTreshold() {
        return onePixelTreshold;
    }

    public void setOnePixelTreshold(short onePixelTreshold) {
        this.onePixelTreshold = onePixelTreshold;
    }

    public Short getGlobalDifferenceTreshold() {
        return globalDifferenceTreshold;
    }

    public void setGlobalDifferenceTreshold(short globalDifferenceTreshold) {
        this.globalDifferenceTreshold = globalDifferenceTreshold;
    }

    public Long getGlobalDifferencePixelAmount() {
        return globalDifferencePixelAmount;
    }

    public void setGlobalDifferencePixelAmount(long globalDifferencePixelAmount) {
        this.globalDifferencePercentage = null;
        this.globalDifferencePixelAmount = globalDifferencePixelAmount;
    }

    public Short getGlobalDifferencePercentage() {
        return globalDifferencePercentage;
    }

    public void setGlobalDifferencePercentage(short globalDifferencePercentage) {
        this.globalDifferencePixelAmount = null;
        this.globalDifferencePercentage = globalDifferencePercentage;
    }

    public void setDefaultValuesForUnset() {
        if (onePixelTreshold == null) {
            onePixelTreshold = 0;
        }
        if (globalDifferenceTreshold == null) {
            globalDifferenceTreshold = 0;
        }
        if (globalDifferencePercentage == null && globalDifferencePixelAmount == null) {
            globalDifferencePixelAmount = 0L;
        }
    }

    public void setValuesFromParent(Perception parent) {
        this.onePixelTreshold = parent.onePixelTreshold;
        this.globalDifferenceTreshold = parent.globalDifferenceTreshold;
        if (parent.globalDifferencePixelAmount != null) {
            this.setGlobalDifferencePixelAmount(parent.globalDifferencePixelAmount);
        }
        if (parent.globalDifferencePercentage != null) {
            this.setGlobalDifferencePercentage(parent.globalDifferencePercentage);
        }
    }
}
