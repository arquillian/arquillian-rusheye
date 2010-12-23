package org.jboss.rusheye.suite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TypeProperties")
@XmlSeeAlso({ Listener.class, MaskRetriever.class, PatternRetriever.class, SampleRetriever.class })
public abstract class TypeProperties extends Properties {

    protected String type;

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }
}
