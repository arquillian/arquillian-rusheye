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
@XmlType(name = "MaskRetriever")
public class MaskRetriever extends TypeProperties implements org.jboss.lupic.retriever.MaskRetriever {

    @XmlTransient
    org.jboss.lupic.retriever.MaskRetriever maskRetriever;

    public void initializeRetriever() {
        if (maskRetriever == null) {
            Validate.notNull(getType());
            maskRetriever = new Instantiator<org.jboss.lupic.retriever.MaskRetriever>().getInstance(getType());
            maskRetriever.setGlobalProperties(this);
        }
    }

    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        initializeRetriever();
        return maskRetriever.retrieve(source, localProperties);
    }

    @Override
    public Properties mergeProperties(Properties localProperties) {
        initializeRetriever();
        return maskRetriever.mergeProperties(localProperties);
    }

    @Override
    public void setGlobalProperties(Properties properties) {
        initializeRetriever();
        maskRetriever.setGlobalProperties(properties);
    }

}
