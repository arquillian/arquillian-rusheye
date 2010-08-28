package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.jboss.lupic.retriever.Retriever;

public class Pattern extends FutureTask<BufferedImage> {

    private String name;
    private Properties properties;

    public Pattern(String name, final String source, final Properties patternProperties, final Retriever retriever) {
        super(new Callable<BufferedImage>() {
            @Override
            public BufferedImage call() throws Exception {
                return retriever.retrieve(source, patternProperties);
            }
        });
        this.name = name;
        this.properties = patternProperties;
    }

    public String getName() {
        return name;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pattern other = (Pattern) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
