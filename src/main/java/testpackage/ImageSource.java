package testpackage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageSource", propOrder = { "any" })
@XmlSeeAlso({ Mask.class, Pattern.class })
public abstract class ImageSource {

    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute
    protected String source;

    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }
}
