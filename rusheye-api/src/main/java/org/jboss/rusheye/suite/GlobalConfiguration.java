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

import org.jboss.rusheye.internal.Instantiator;
import org.jboss.rusheye.listener.SuiteListener;

/**
 * <p>
 * Encapsulates global configuration of the suite, containing all the settings which applies to whole run of the suite.
 * </p>
 * 
 * <p>
 * In addition to {@link Configuration} it contains information about retrievers and suite listeners.
 * </p>
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "GlobalConfiguration", propOrder = { "listeners", "patternRetriever", "maskRetriever",
        "sampleRetriever" })
@XmlRootElement(name = "global-configuration")
public class GlobalConfiguration extends Configuration {

    /** The listeners. */
    protected List<Listener> listeners;

    /** The pattern retriever. */
    protected PatternRetriever patternRetriever;

    /** The mask retriever. */
    protected MaskRetriever maskRetriever;

    /** The sample retriever. */
    protected SampleRetriever sampleRetriever;

    /** The suite listeners. */
    @XmlTransient
    private Map<String, SuiteListener> suiteListeners = new HashMap<String, SuiteListener>();

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
    @XmlElement(name = "listener")
    public List<Listener> getListeners() {
        if (listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        return this.listeners;
    }

    /**
     * Gets the pattern retriever.
     * 
     * @return the pattern retriever
     */
    @XmlElement(name = "pattern-retriever")
    public PatternRetriever getPatternRetriever() {
        return patternRetriever;
    }

    /**
     * Sets the pattern retriever.
     * 
     * @param value
     *            the new pattern retriever
     */
    public void setPatternRetriever(PatternRetriever value) {
        this.patternRetriever = value;
    }

    /**
     * Gets the mask retriever.
     * 
     * @return the mask retriever
     */
    @XmlElement(name = "mask-retriever")
    public MaskRetriever getMaskRetriever() {
        return maskRetriever;
    }

    /**
     * Sets the mask retriever.
     * 
     * @param value
     *            the new mask retriever
     */
    public void setMaskRetriever(MaskRetriever value) {
        this.maskRetriever = value;
    }

    /**
     * Gets the sample retriever.
     * 
     * @return the sample retriever
     */
    @XmlElement(name = "sample-retriever")
    public SampleRetriever getSampleRetriever() {
        return sampleRetriever;
    }

    /**
     * Sets the sample retriever.
     * 
     * @param value
     *            the new sample retriever
     */
    public void setSampleRetriever(SampleRetriever value) {
        this.sampleRetriever = value;
    }

    /**
     * Gets the configured listeners.
     * 
     * @return the configured listeners
     */
    public Collection<SuiteListener> getConfiguredListeners() {
        for (Listener listener : listeners) {
            final String type = listener.getType();
            if (!suiteListeners.containsKey(type)) {
                SuiteListener parserListener = new Instantiator<SuiteListener>().getInstance(type);
                parserListener.setProperties(listener);
                suiteListeners.put(type, parserListener);
            }
        }
        return suiteListeners.values();
    }
}
