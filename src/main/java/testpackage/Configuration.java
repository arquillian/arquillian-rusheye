package testpackage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Configuration", propOrder = { "perception", "mask" })
@XmlSeeAlso({ GlobalConfiguration.class, Test.class })
public abstract class Configuration {

    protected Perception perception;
    protected List<Mask> mask;

    public Perception getPerception() {
        return perception;
    }

    public void setPerception(Perception value) {
        this.perception = value;
    }

    public List<Mask> getMask() {
        if (mask == null) {
            mask = new ArrayList<Mask>();
        }
        return this.mask;
    }
}
