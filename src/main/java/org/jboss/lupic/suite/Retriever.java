package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.Validate;
import org.jboss.lupic.retriever.MaskRetriever;
import org.jboss.lupic.retriever.PatternRetriever;
import org.jboss.lupic.retriever.RetrieverException;
import org.jboss.lupic.retriever.sample.SampleRetriever;
import org.jboss.lupic.suite.utils.Instantiator;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Retriever")
@XmlSeeAlso({ SampleRetriever.class })
public class Retriever extends TypeProperties implements PatternRetriever, MaskRetriever {

    org.jboss.lupic.retriever.Retriever retriever;
    
    public Retriever() {
        
    }
    
    @Override
    public void setType(String value) {
        super.setType(value);
        Validate.notNull(type);
        retriever = new Instantiator<org.jboss.lupic.retriever.Retriever>().getInstance(type);
    }
    
    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        return retriever.retrieve(source, localProperties);
    }

    @Override
    public Properties mergeProperties(Properties localProperties) {
        return retriever.mergeProperties(localProperties);
    }

    @Override
    public void setGlobalProperties(Properties properties) {
        retriever.setGlobalProperties(properties);
    }

}
