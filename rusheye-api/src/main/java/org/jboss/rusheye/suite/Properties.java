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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.jboss.rusheye.internal.ElementAdapter;
import org.jboss.rusheye.internal.FilterCollection;
import org.jboss.rusheye.internal.Predicate;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Properties", propOrder = { "any" })
@XmlSeeAlso({ TypeProperties.class, ImageSource.class })
public class Properties {

    protected List<Element> any;

    @XmlAnyElement(lax = false)
    public List<Element> getAny() {
        if (any == null) {
            any = new ArrayList<Element>();
        }
        return this.any;
    }

    /*
     * logic
     */
    public Object getProperty(final String key) {
        Collection<Element> elements = FilterCollection.filter(getAny(), new Predicate<Element>() {
            @Override
            public boolean apply(Element element) {
                return element.getLocalName().equals(key);
            }
        });
        if (elements.isEmpty()) {
            return null;
        }
        return elements.iterator().next().getTextContent();
    }

    public void setProperty(String key, Object value) {
        Element elementForRemoval = null;
        for (Element element : getAny()) {
            if (element.getLocalName().equals(key)) {
                elementForRemoval = element;
            }
        }
        if (elementForRemoval != null) {
            getAny().remove(elementForRemoval);
        }
        Element element = new ElementAdapter(key);
        element.setTextContent(value.toString());
        getAny().add(element);
    }

    public void include(Properties includeProperties) {
        for (Element element : includeProperties.getAny()) {
            setProperty(element.getLocalName(), element.getTextContent());
        }
    }

    public int size() {
        return getAny().size();
    }
}
