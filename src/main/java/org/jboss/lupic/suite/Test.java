package org.jboss.lupic.suite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Test", propOrder = { "pattern" })
public class Test extends Configuration {

    @XmlElement(name="pattern", required = true)
    protected List<Pattern> patterns;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    protected String name;

    /*
     * accessors
     */
    public List<Pattern> getPatterns() {
        if (patterns == null) {
            patterns = new ArrayList<Pattern>();
        }
        return this.patterns;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }
    
    /*
     * logic
     */
    public Sample getSample() {
        Sample sample = new Sample();
        sample.setSource(name);
        return sample;
    }
    

}
