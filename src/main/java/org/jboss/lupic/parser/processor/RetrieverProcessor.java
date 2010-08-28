package org.jboss.lupic.parser.processor;

import org.apache.commons.lang.Validate;
import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.retriever.Retriever;
import org.jboss.lupic.suite.GlobalConfiguration;

public class RetrieverProcessor extends Processor {

    Retriever retriever;

    {
        setPropertiesEnabled(true);
    }

    @Override
    public void start() {
        String retrieverClassName = getAttribute("class");
        Validate.notNull(retrieverClassName,
            "image-retriever must have class attribute defined pointing to Retriever implementation");

        retriever = getRetrieverInstance(retrieverClassName);

        String tagName = getTagName();
        GlobalConfiguration globalConfiguration = getVisualSuite().getGlobalConfiguration();

        if ("mask-retriever".equals(tagName)) {
            globalConfiguration.setMaskRetriever(retriever);
        } else if ("image-retriever".equals(tagName)) {
            globalConfiguration.setImageRetriever(retriever);
        } else {
            throw new IllegalStateException("unsupported retriever tag name '" + tagName + "'");
        }
    }

    @Override
    public void end() {
        retriever.setGlobalProperties(getProperties());
    }

    private Retriever getRetrieverInstance(String retrieverClassName) {
        try {
            return getRetriverClass(retrieverClassName).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Retriever> getRetriverClass(String retrieverClassName) {
        try {
            return (Class<? extends Retriever>) Class.forName(retrieverClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
