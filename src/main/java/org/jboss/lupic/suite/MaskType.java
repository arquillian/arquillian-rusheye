package org.jboss.lupic.suite;

public enum MaskType {
    IGNORE_BITMAP, SELECTIVE_ALPHA;

    public String toXmlId() {
        return this.toString().toLowerCase().replace("_", "-");
    }

    public static MaskType fromXmlId(String xmlId) {
        return MaskType.valueOf(xmlId.toUpperCase().replace("-", "_"));
    }

}
