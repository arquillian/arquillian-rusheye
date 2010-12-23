package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.Validate;
import org.jboss.lupic.retriever.RetrieverException;
import org.jboss.lupic.suite.utils.Instantiator;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "PatternRetriever")
public class PatternRetriever extends TypeProperties implements org.jboss.lupic.retriever.PatternRetriever {

    @XmlTransient
    org.jboss.lupic.retriever.PatternRetriever patternRetriever;

    public void initializeRetriever() {
        if (patternRetriever == null) {
            Validate.notNull(getType());
            patternRetriever = new Instantiator<org.jboss.lupic.retriever.PatternRetriever>().getInstance(getType());
            patternRetriever.setGlobalProperties(this);
        }
    }

    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        initializeRetriever();
        return patternRetriever.retrieve(source, localProperties);
    }

    @Override
    public Properties mergeProperties(Properties localProperties) {
        initializeRetriever();
        return patternRetriever.mergeProperties(localProperties);
    }

    @Override
    public void setGlobalProperties(Properties properties) {
        initializeRetriever();
        patternRetriever.setGlobalProperties(properties);
    }

}
