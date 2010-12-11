package testpackage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalConfiguration", propOrder = { "listeners", "patternRetriever", "maskRetriever",
    "sampleRetriever" })
public class GlobalConfiguration extends Configuration {

    protected List<Listener> listeners;
    @XmlElement(name = "pattern-retriever")
    protected Retriever patternRetriever;
    @XmlElement(name = "mask-retriever")
    protected Retriever maskRetriever;
    @XmlElement(name = "sample-retriever")
    protected SampleRetriever sampleRetriever;

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
}
