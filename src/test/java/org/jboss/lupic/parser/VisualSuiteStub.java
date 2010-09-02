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

import java.util.Iterator;

import org.dom4j.Document;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jboss.lupic.retriever.FileRetriever;

import static org.jboss.lupic.parser.VisualSuiteDefinitions.*;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@SuppressWarnings("unchecked")
public class VisualSuiteStub {

    Document document = DocumentHelper.createDocument();

    Element visualSuite = document.addElement(VISUAL_SUITE)
        .addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")
        .addAttribute("xsi:schemaLocation", LUPIC_NS.getURI() + " src/main/resources/visual-suite.xsd");

    Element globalConfiguration = visualSuite.addElement(GLOBAL_CONFIGURATION);
    Element listeners = globalConfiguration.addElement(LISTENERS);
    Element imageRetriever = globalConfiguration.addElement(IMAGE_RETRIEVER).addAttribute("class",
        FileRetriever.class.getName());
    Element maskRetriever = globalConfiguration.addElement(MASK_RETRIEVER).addAttribute("class",
        FileRetriever.class.getName());
    Element perception = globalConfiguration.addElement(PERCEPTION);

    Element defaultListener = listeners.addElement(LISTENER).addAttribute("class",
        ParserListenerAdapter.class.getName());

    Element defaultTest = visualSuite.addElement(TEST).addAttribute("name", "default-test");

    Element defaultPattern = defaultTest.addElement(PATTERN).addAttribute("name", "default-test-pattern");

    {
        Iterator<Element> iterator = visualSuite.elementIterator(TEST);
        if (iterator.next() != defaultTest) {
            throw new IllegalStateException();
        }
        if (iterator.hasNext()) {
            visualSuite.remove(defaultTest);
        }
    }

}
