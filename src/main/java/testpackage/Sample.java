package testpackage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sample")
public class Sample {

    @XmlAttribute
    protected String source;

    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }

}
