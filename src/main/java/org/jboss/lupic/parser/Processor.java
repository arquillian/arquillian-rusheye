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
package org.jboss.lupic.parser;

import java.util.HashMap;
import java.util.Map;

import org.jboss.lupic.parser.processor.PropertiesProcessor;
import org.jboss.lupic.suite.Properties;
import org.jboss.lupic.suite.VisualSuite;
import org.xml.sax.Attributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class Processor {

    private Map<String, Class<? extends Processor>> processorMap = new HashMap<String, Class<? extends Processor>>();
    private boolean propertiesEnabled = false;

    private String tagName;
    private Context context;
    private VisualSuite visualSuite;
    private Attributes attributes;
    private Properties properties;

    public void start() {
    }

    public void process(String content) {
    }

    public void end() {
    }

    protected String getTagName() {
        return tagName;
    }

    protected String getAttribute(String localName) {
        return attributes.getValue("", localName);
    }

    protected Properties getProperties() {
        return properties;
    }

    void setContext(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    void setVisualSuite(VisualSuite visualSuite) {
        this.visualSuite = visualSuite;
    }

    protected VisualSuite getVisualSuite() {
        return visualSuite;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    protected void supportProcessor(String tagName, Class<? extends Processor> processorClass) {
        this.processorMap.put(tagName, processorClass);
    }

    protected void setPropertiesEnabled(boolean propertiesEnabled) {
        this.propertiesEnabled = propertiesEnabled;
    }

    public Processor getProcessor(String tagName) {
        Class<? extends Processor> processorClass = processorMap.get(tagName);

        if (processorClass == null && propertiesEnabled) {
            if (properties == null) {
                properties = new Properties();
            }
            return new PropertiesProcessor(properties, tagName);
        }

        if (processorClass == null) {
            throw new IllegalStateException("The target processor for tag name '" + tagName + "' doesn't exists");
        }

        try {
            Processor processor = processorClass.newInstance();
            processor.tagName = tagName;
            return processor;
        } catch (InstantiationException e) {
            throw new IllegalStateException();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException();
        }
    }
}
