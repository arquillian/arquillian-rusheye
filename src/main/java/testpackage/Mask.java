package testpackage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Mask")
public class Mask extends ImageSource {

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    protected String id;
    @XmlAttribute(required = true)
    protected MaskType type;
    @XmlAttribute(name = "vertical-align")
    protected VerticalAlign verticalAlign;
    @XmlAttribute(name = "horizontal-align")
    protected HorizontalAlign horizontalAlign;

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public MaskType getType() {
        return type;
    }

    public void setType(MaskType value) {
        this.type = value;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign value) {
        this.verticalAlign = value;
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(HorizontalAlign value) {
        this.horizontalAlign = value;
    }

}
