package org.jboss.lupic.suite;

public class Perception {
	short onePixelTreshold = 0;
	short globalDifferenceTreshold = 0;
	Long globalDifferencePixelAmount = 0l;
	Short globalDifferencePercentage = null;

	public short getOnePixelTreshold() {
		return onePixelTreshold;
	}

	public void setOnePixelTreshold(short onePixelTreshold) {
		this.onePixelTreshold = onePixelTreshold;
	}

	public short getGlobalDifferenceTreshold() {
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

}
