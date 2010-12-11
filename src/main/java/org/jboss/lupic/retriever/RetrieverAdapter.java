package org.jboss.lupic.retriever;

import java.awt.image.BufferedImage;
import java.util.Properties;

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
