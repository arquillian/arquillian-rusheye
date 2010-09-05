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
package org.jboss.lupic.parser.processor;

import org.apache.commons.lang.Validate;
import org.jboss.lupic.exception.LupicConfigurationException;
import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.retriever.MaskRetriever;
import org.jboss.lupic.retriever.PatternRetriever;
import org.jboss.lupic.retriever.Retriever;
import org.jboss.lupic.retriever.sample.SampleRetriever;
import org.jboss.lupic.suite.GlobalConfiguration;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class RetrieverProcessor extends Processor {

    Retriever retriever;

    {
        setPropertiesEnabled(true);
    }

    @Override
    public void start() {
        String retrieverClassName = getAttribute("class");
        Validate.notNull(retrieverClassName,
            "image-retriever must have class attribute defined pointing to Retriever implementation");

        retriever = getRetrieverInstance(retrieverClassName);

        String tagName = getTagName();
        GlobalConfiguration globalConfiguration = getVisualSuite().getGlobalConfiguration();

        if ("mask-retriever".equals(tagName)) {
            if (!MaskRetriever.class.isAssignableFrom(retriever.getClass())) {
                throw new LupicConfigurationException("Retriever " + retriever.getClass().getName()
                    + " is not instance of MaskRetriever");
            }
            globalConfiguration.setMaskRetriever((MaskRetriever) retriever);
        } else if ("pattern-retriever".equals(tagName)) {
            if (!PatternRetriever.class.isAssignableFrom(retriever.getClass())) {
                throw new LupicConfigurationException("Retriever " + retriever.getClass().getName()
                    + " is not instance of PatternRetriever");
            }
            globalConfiguration.setPatternRetriever((PatternRetriever) retriever);
        } else if ("sample-retriever".equals(tagName)) {
            if (!SampleRetriever.class.isAssignableFrom(retriever.getClass())) {
                throw new LupicConfigurationException("Retriever " + retriever.getClass().getName()
                    + " is not instance of SampleRetriever");
            }
            globalConfiguration.setSampleRetriever((SampleRetriever) retriever);
        } else {
            throw new LupicConfigurationException("unsupported retriever tag name '" + tagName + "'");
        }
    }

    @Override
    public void end() {
        retriever.setGlobalProperties(getProperties());
    }

    private Retriever getRetrieverInstance(String retrieverClassName) {
        try {
            return getRetriverClass(retrieverClassName).newInstance();
        } catch (InstantiationException e) {
            throw new LupicConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new LupicConfigurationException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Retriever> getRetriverClass(String retrieverClassName) {
        try {
            return (Class<? extends Retriever>) Class.forName(retrieverClassName);
        } catch (ClassNotFoundException e) {
            throw new LupicConfigurationException(e);
        }
    }
}
