package org.jboss.lupic.suite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.lupic.parser.listener.ParserListener;
import org.jboss.lupic.suite.utils.Instantiator;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalConfiguration", propOrder = { "listeners", "patternRetriever", "maskRetriever",
    "sampleRetriever" })
@XmlRootElement(name = "global-configuration")
public class GlobalConfiguration extends Configuration {

    @XmlElement(name = "listener")
    protected List<Listener> listeners;
    @XmlElement(name = "pattern-retriever")
    protected Retriever patternRetriever;
    @XmlElement(name = "mask-retriever")
    protected Retriever maskRetriever;
    @XmlElement(name = "sample-retriever")
    protected SampleRetriever sampleRetriever;

    /*
     * accessors
     */
    public List<Listener> getListeners() {
        if (listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        return this.listeners;
    }

    public Retriever getPatternRetriever() {
        return patternRetriever;
    }

    public void setPatternRetriever(Retriever value) {
        this.patternRetriever = value;
    }

    public Retriever getMaskRetriever() {
        return maskRetriever;
    }

    public void setMaskRetriever(Retriever value) {
        this.maskRetriever = value;
    }

    public SampleRetriever getSampleRetriever() {
        return sampleRetriever;
    }

    public void setSampleRetriever(SampleRetriever value) {
        this.sampleRetriever = value;
    }

    /*
     * logic
     */
    @XmlTransient
    private Map<String, ParserListener> parserListeners = new HashMap<String, ParserListener>();

    public Collection<ParserListener> getConfiguredListeners() {
        for (Listener listener : listeners) {
            final String type = listener.getType();
            if (!parserListeners.containsKey(type)) {
                ParserListener parserListener = new Instantiator<ParserListener>().getInstance(type);
                parserListener.setProperties(listener);
                parserListeners.put(type, parserListener);
            }
        }
        return parserListeners.values();
    }
}
