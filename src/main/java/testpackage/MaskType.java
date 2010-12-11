package testpackage;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "MaskType")
@XmlEnum
public enum MaskType {

    @XmlEnumValue("selective-alpha")
    SELECTIVE_ALPHA("selective-alpha"), @XmlEnumValue("ignore-bitmap")
    IGNORE_BITMAP("ignore-bitmap");
    private final String value;

    MaskType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MaskType fromValue(String v) {
        for (MaskType c : MaskType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
