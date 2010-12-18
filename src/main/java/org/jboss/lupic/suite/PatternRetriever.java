package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.Validate;
import org.jboss.lupic.retriever.RetrieverException;
import org.jboss.lupic.suite.utils.Instantiator;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatternRetriever")
public class PatternRetriever extends TypeProperties implements org.jboss.lupic.retriever.PatternRetriever {

    @XmlTransient
    org.jboss.lupic.retriever.PatternRetriever patternRetriever;

    @Override
    public void setType(String value) {
        super.setType(value);
        Validate.notNull(type);
        patternRetriever = new Instantiator<org.jboss.lupic.retriever.PatternRetriever>().getInstance(type);
    }

    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        return patternRetriever.retrieve(source, localProperties);
    }

    @Override
    public Properties mergeProperties(Properties localProperties) {
        return patternRetriever.mergeProperties(localProperties);
    }

    @Override
    public void setGlobalProperties(Properties properties) {
        patternRetriever.setGlobalProperties(properties);
    }

}
