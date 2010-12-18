package org.jboss.lupic.parser;

import java.util.LinkedList;
import java.util.List;

import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.Perception;

public class DefaultConfiguration extends Configuration {

    private static final List<Mask> EMPTY_MASKS = new LinkedList<Mask>();
    private static final Perception DEFAULT_PERCEPTION = new Perception();

    static {
        DEFAULT_PERCEPTION.setOnePixelTreshold(0);
        DEFAULT_PERCEPTION.setGlobalDifferenceTreshold(0);
        DEFAULT_PERCEPTION.setGlobalDifferenceAmount("0px");
    }
    
    {
        perception = DEFAULT_PERCEPTION;
        masks = EMPTY_MASKS;
    }

    @Override
    public void setPerception(Perception value) {
        throw new UnsupportedOperationException("Default configuration doesn't support direct assignment of perception");
    }
}
