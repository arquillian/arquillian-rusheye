package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.lupic.result.ResultConclusion;
import org.jboss.lupic.retriever.PatternRetriever;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Pattern")
public class Pattern extends ImageSource {

    protected String name;
    protected ComparisonResult comparisonResult;
    private ResultConclusion conclusion;
    private String output;

    @Resource
    @XmlTransient
    public PatternRetriever patternRetriever;

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlElement
    public ComparisonResult getComparisonResult() {
        return comparisonResult;
    }

    public void setComparisonResult(ComparisonResult comparisonResult) {
        this.comparisonResult = comparisonResult;
    }

    @XmlAttribute(name = "result")
    public ResultConclusion getConclusion() {
        return this.conclusion;
    }

    public void setConclusion(ResultConclusion conclusion) {
        this.conclusion = conclusion;
    }

    @XmlAttribute
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    /*
     * logic
     */
    @Override
    public BufferedImage retrieve() throws Exception {
        return patternRetriever.retrieve(source, this);
    }

}
