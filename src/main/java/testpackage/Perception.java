package testpackage;

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
    protected String globalDifferencePixelAmount;

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

    public String getGlobalDifferencePixelAmount() {
        return globalDifferencePixelAmount;
    }

    public void setGlobalDifferencePixelAmount(String value) {
        this.globalDifferencePixelAmount = value;
    }

}
