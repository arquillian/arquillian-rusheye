package org.jboss.lupic.suite;

public class Perception {
	short onePixelTreshold = 0;
	short globalDifferenceTreshold = 0;
	Short globalDifferencePixelAmount = 0;
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

	public Short getGlobalDifferencePixelAmount() {
		return globalDifferencePixelAmount;
	}

	public void setGlobalDifferencePixelAmount(
			short globalDifferencePixelAmount) {
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
