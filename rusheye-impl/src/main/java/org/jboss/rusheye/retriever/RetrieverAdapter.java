package org.jboss.rusheye.retriever;

import java.awt.image.BufferedImage;

import org.jboss.rusheye.suite.Properties;

public class RetrieverAdapter implements Retriever {

    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        return null;
    }

    public Properties mergeProperties(Properties localProperties) {
        return null;
    }

    public void setGlobalProperties(Properties properties) {
    }

}
