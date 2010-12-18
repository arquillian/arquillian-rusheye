package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.lupic.retriever.PatternRetriever;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pattern")
public class Pattern extends ImageSource {

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    protected String name;

    @Resource
    public PatternRetriever patternRetriever;
    
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }
    
    /*
     * logic
     */
    @Override
    public BufferedImage retrieve() throws Exception {
        return patternRetriever.retrieve(source, this);
    }

}
