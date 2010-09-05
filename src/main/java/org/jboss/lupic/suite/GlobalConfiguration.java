/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
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
package org.jboss.lupic.suite;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jboss.lupic.parser.listener.ParserListener;
import org.jboss.lupic.retriever.MaskRetriever;
import org.jboss.lupic.retriever.PatternRetriever;
import org.jboss.lupic.retriever.sample.SampleRetriever;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class GlobalConfiguration extends Configuration {
    PatternRetriever patternRetriever;
    MaskRetriever maskRetriever;
    SampleRetriever sampleRetriever;
    Set<ParserListener> configuredListeners = new LinkedHashSet<ParserListener>();

    public PatternRetriever getPatternRetriever() {
        return patternRetriever;
    }

    public void setPatternRetriever(PatternRetriever patternRetriever) {
        this.patternRetriever = patternRetriever;
    }

    public MaskRetriever getMaskRetriever() {
        return maskRetriever;
    }

    public void setMaskRetriever(MaskRetriever maskRetriever) {
        this.maskRetriever = maskRetriever;
    }

    public SampleRetriever getSampleRetriever() {
        return sampleRetriever;
    }

    public void setSampleRetriever(SampleRetriever sampleRetriever) {
        this.sampleRetriever = sampleRetriever;
    }

    public Set<ParserListener> getConfiguredListeners() {
        return configuredListeners;
    }
}
