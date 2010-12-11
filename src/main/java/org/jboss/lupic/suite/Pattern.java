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

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.dom4j.dom.DOMElement;
import org.dom4j.dom.DOMText;
import org.jboss.lupic.retriever.Retriever;
import org.jboss.lupic.suite.utils.Nullify;
import org.jboss.lupic.suite.utils.VisualSuiteResult;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Pattern {

    private String name;
    private String source;
    private Properties properties = new Properties();
    private Retriever retriever;

    FutureTask<BufferedImage> futureTask = new FutureTask<BufferedImage>(new Callable<BufferedImage>() {
        @Override
        public BufferedImage call() throws Exception {
            return retriever.retrieve(source, properties);
        }
    });
    
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlAttribute
    @Nullify(VisualSuiteResult.class)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    @XmlAnyElement(lax=true)
    public List<Object> getOthers() {
        List<Object> elements = new LinkedList<Object>();
        for (Entry<Object, Object> entry : properties.entrySet()) {
            DOMElement element = new DOMElement((String) entry.getKey());
            DOMText text = new DOMText((String) entry.getValue());
            element.appendChild(text);
            elements.add(element);
        }
        return elements;
    }
    
    public void setOthers(List<Object> others) {
        // TODO
    }
    
    @XmlTransient
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @XmlTransient
    public Retriever getRetriever() {
        return retriever;
    }

    public void setRetriever(Retriever retriever) {
        this.retriever = retriever;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pattern other = (Pattern) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public void run() {
        futureTask.run();
    }

    public BufferedImage get() throws InterruptedException, ExecutionException {
        return futureTask.get();
    }
}
