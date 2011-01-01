/**
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.rusheye.suite;

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

import org.jboss.rusheye.result.ResultConclusion;
import org.jboss.rusheye.retriever.PatternRetriever;

/**
 * The pattern as input into comparison process, to be compared with sample.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Pattern")
public class Pattern extends ImageSource {

    /** The name. */
    protected String name;

    /** The comparison result. */
    protected ComparisonResult comparisonResult;

    /** The conclusion. */
    private ResultConclusion conclusion;

    /** The output. */
    private String output;

    /** The pattern retriever - needs to be injected from outside to let pattern work correctly. */
    @Resource
    @XmlTransient
    private PatternRetriever patternRetriever;

    /**
     * Gets the name.
     * 
     * @return the name
     */
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param value
     *            the new name
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the comparison result.
     * 
     * @return the comparison result
     */
    @XmlElement(name = "comparison-result")
    public ComparisonResult getComparisonResult() {
        return comparisonResult;
    }

    /**
     * Sets the comparison result.
     * 
     * @param comparisonResult
     *            the new comparison result
     */
    public void setComparisonResult(ComparisonResult comparisonResult) {
        this.comparisonResult = comparisonResult;
    }

    /**
     * Gets the conclusion.
     * 
     * @return the conclusion
     */
    @XmlAttribute(name = "result")
    public ResultConclusion getConclusion() {
        return this.conclusion;
    }

    /**
     * Sets the conclusion.
     * 
     * @param conclusion
     *            the new conclusion
     */
    public void setConclusion(ResultConclusion conclusion) {
        this.conclusion = conclusion;
    }
    
    /**
     * Gets the output.
     * 
     * @return the output
     */
    @XmlAttribute
    public String getOutput() {
        return output;
    }

    /**
     * Sets the output.
     * 
     * @param output
     *            the new output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.rusheye.suite.ImageSource#retrieve()
     */
    @Override
    public BufferedImage retrieve() throws Exception {
        return patternRetriever.retrieve(source, this);
    }

}
