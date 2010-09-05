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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jboss.lupic.parser.listener.ParserListener;
import org.jboss.lupic.parser.listener.ParserListenerAdapter;
import org.jboss.lupic.suite.VisualSuite;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class Parser {

    public static final String URI = "http://www.jboss.org/test/visual-suite";

    private static final String XML_VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    private static final String XML_SCHEMA_FEATURE = "http://apache.org/xml/features/validation/schema";
    private static final String XML_SCHEMA_FULL_CHECKING_FEATURE = "http://apache.org/xml/features/validation/schema-full-checking";

    private Set<ParserListener> listeners = new LinkedHashSet<ParserListener>();
    private Handler handler = new Handler(listeners);
    private XMLReader reader;

    public Parser() {
        this.registerListener(new ParserListenerRegistrationListener());
        try {
            reader = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            throw new IllegalStateException("Cannot create XMLReader", e);
        }
        try {
            reader.setFeature(XML_VALIDATION_FEATURE, true);
            reader.setFeature(XML_SCHEMA_FEATURE, true);
            reader.setFeature(XML_SCHEMA_FULL_CHECKING_FEATURE, true);
        } catch (SAXException e) {
            throw new IllegalStateException("Cannot configure XMLReader to validate input", e);
        }
        reader.setContentHandler(handler);
    }

    public void parseResource(String resourceName) throws SAXException, IOException {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        parseStream(inputStream);
    }

    public void parseFile(File file) throws SAXException, IOException {
        InputStream inputStream = new FileInputStream(file);
        parseStream(inputStream);
    }

    public void parseStream(InputStream inputStream) throws SAXException, IOException {
        InputSource inputSource = new InputSource(inputStream);
        parseSource(inputSource);
    }

    public void parseSource(InputSource input) throws SAXException, IOException {
        reader.parse(input);
    }

    public void registerListener(ParserListener parserListener) {
        synchronized (parserListener) {
            if (listeners.contains(parserListener)) {
                return;
            }
            listeners.add(parserListener);
        }
    }

    public void unregisterListener(ParserListener parserListener) {
        synchronized (listeners) {
            if (listeners.contains(parserListener)) {
                listeners.remove(parserListener);
            }
        }
        throw new IllegalStateException("Given parser isn't registered");
    }

    public XMLReader getXMLReader() {
        return reader;
    }

    Handler getHandler() {
        return handler;
    }

    private class ParserListenerRegistrationListener extends ParserListenerAdapter {
        @Override
        public void onConfigurationParsed(VisualSuite visualSuite) {
            for (ParserListener listener : visualSuite.getGlobalConfiguration().getConfiguredListeners()) {
                listener.onSuiteStarted(visualSuite);
                listener.onConfigurationParsed(visualSuite);
                Parser.this.registerListener(listener);
            }

            if (listeners.size() == 1) {
                throw new IllegalStateException("No ParserListener was registered to process parsed tests");
            }
        }
    }
}
