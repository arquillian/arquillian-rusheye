package org.jboss.lupic.suite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.lupic.parser.listener.ParserListener;
import org.jboss.lupic.retriever.MaskRetriever;
import org.jboss.lupic.retriever.PatternRetriever;
import org.jboss.lupic.suite.utils.Instantiator;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalConfiguration", propOrder = { "listeners", "patternRetriever", "maskRetriever",
    "sampleRetriever" })
@XmlRootElement(name="global-configuration")
public class GlobalConfiguration extends Configuration {

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
    public Collection<ParserListener> getConfiguredListeners() {
        return Collections2.transform(getListeners(), new Function<Listener, ParserListener>() {
            @Override
            public ParserListener apply(Listener listener) {
                String type = listener.getType();
                ParserListener parserListener = new Instantiator<ParserListener>().getInstance(type);
                parserListener.setProperties(listener);
                return parserListener;
            }
        });
    }
}
