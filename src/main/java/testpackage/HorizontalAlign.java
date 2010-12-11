package testpackage;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HorizontalAlign")
@XmlEnum
public enum HorizontalAlign {

    @XmlEnumValue("left")
    LEFT("left"), @XmlEnumValue("right")
    RIGHT("right");
    private final String value;

    HorizontalAlign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HorizontalAlign fromValue(String v) {
        for (HorizontalAlign c : HorizontalAlign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
