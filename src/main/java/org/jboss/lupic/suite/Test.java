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
    private Sample sample;
    
    public Sample getSample() {
        if (sample == null) {
            sample = new Sample();
            sample.setSource(name);
        }
        return sample;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Test))
            return false;
        Test other = (Test) obj;
        if (getName() == null) {
            if (other.getName() != null)
                return false;
        } else if (!getName().equals(other.getName()))
            return false;
        return true;
    }
}
