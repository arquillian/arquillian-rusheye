package org.jboss.lupic.suite;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "", propOrder = { "globalConfiguration", "test" })
@XmlRootElement(name = "visual-suite")
public class VisualSuite {

    protected GlobalConfiguration globalConfiguration;
    protected List<Test> test;
    protected BigInteger id;

    @XmlElement(name = "global-configuration", required = true)
    public GlobalConfiguration getGlobalConfiguration() {
        return globalConfiguration;
    }

    public void setGlobalConfiguration(GlobalConfiguration value) {
        this.globalConfiguration = value;
    }

    @XmlElement(required = true)
    public List<Test> getTest() {
        if (test == null) {
            test = new ArrayList<Test>();
        }
        return this.test;
    }

    @XmlAttribute
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger value) {
        this.id = value;
    }

}
