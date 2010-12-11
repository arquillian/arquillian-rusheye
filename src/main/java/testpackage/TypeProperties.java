package testpackage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeProperties")
@XmlSeeAlso({ Listener.class, Retriever.class })
public abstract class TypeProperties extends Properties {

    @XmlAttribute
    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

}
