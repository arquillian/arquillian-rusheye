package testpackage;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "VerticalAlign")
@XmlEnum
public enum VerticalAlign {

    @XmlEnumValue("top")
    TOP("top"), @XmlEnumValue("bottom")
    BOTTOM("bottom");
    private final String value;

    VerticalAlign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerticalAlign fromValue(String v) {
        for (VerticalAlign c : VerticalAlign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
