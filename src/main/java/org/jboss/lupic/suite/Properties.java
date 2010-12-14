package org.jboss.lupic.suite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Properties", propOrder = { "any" })
@XmlSeeAlso({ TypeProperties.class })
public class Properties {

    @XmlAnyElement(lax = true)
    protected List<Element> any;

    public List<Element> getAny() {
        if (any == null) {
            any = new ArrayList<Element>();
        }
        return this.any;
    }
    
    /*
     * logic
     */
    public String getProperty(final String key) {
        return Collections2.filter(any, new Predicate<Element>() {
            @Override
            public boolean apply(Element element) {
                return element.getLocalName().equals(key);
            }
        }).iterator().next().getTextContent();
    }
}
