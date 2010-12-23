package org.jboss.rusheye.suite;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Perception createPerception() {
        return new Perception();
    }

    public VisualSuite createVisualSuite() {
        return new VisualSuite();
    }

    public Listener createListener() {
        return new Listener();
    }

    public SampleRetriever createSampleRetriever() {
        return new SampleRetriever();
    }

    public Sample createSample() {
        return new Sample();
    }

    public Mask createMask() {
        return new Mask();
    }

    public Pattern createPattern() {
        return new Pattern();
    }

    public Test createTest() {
        return new Test();
    }

    public PatternRetriever createPatternRetriever() {
        return new PatternRetriever();
    }

    public MaskRetriever createMaskRetriever() {
        return new MaskRetriever();
    }

    public GlobalConfiguration createGlobalConfiguration() {
        return new GlobalConfiguration();
    }

    public Properties createProperties() {
        return new Properties();
    }

}
