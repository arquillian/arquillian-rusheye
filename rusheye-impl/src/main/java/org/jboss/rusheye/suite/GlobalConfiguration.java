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

import org.jboss.rusheye.parser.listener.ParserListener;
import org.jboss.rusheye.suite.utils.Instantiator;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "GlobalConfiguration", propOrder = { "listeners", "patternRetriever", "maskRetriever",
        "sampleRetriever" })
@XmlRootElement(name = "global-configuration")
public class GlobalConfiguration extends Configuration {

    protected List<Listener> listeners;
    protected PatternRetriever patternRetriever;
    protected MaskRetriever maskRetriever;
    protected SampleRetriever sampleRetriever;

    @XmlTransient
    private Map<String, ParserListener> parserListeners = new HashMap<String, ParserListener>();

    /*
     * accessors
     */
    @XmlElement(name = "listener")
    public List<Listener> getListeners() {
        if (listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        return this.listeners;
    }

    @XmlElement(name = "pattern-retriever")
    public PatternRetriever getPatternRetriever() {
        return patternRetriever;
    }

    public void setPatternRetriever(PatternRetriever value) {
        this.patternRetriever = value;
    }

    @XmlElement(name = "mask-retriever")
    public MaskRetriever getMaskRetriever() {
        return maskRetriever;
    }

    public void setMaskRetriever(MaskRetriever value) {
        this.maskRetriever = value;
    }

    @XmlElement(name = "sample-retriever")
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
