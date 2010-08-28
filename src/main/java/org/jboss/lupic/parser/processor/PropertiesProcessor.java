package org.jboss.lupic.parser.processor;

import java.util.Properties;

import org.jboss.lupic.parser.Processor;

public class PropertiesProcessor extends Processor {

    private String tagName;
    private Properties properties;

    public PropertiesProcessor(Properties properties, String tagName) {
        this.properties = properties;
        this.tagName = tagName;
        this.properties.put(tagName, "");
    }

    @Override
    public void process(String content) {
        properties.put(tagName, content);
    }
}
