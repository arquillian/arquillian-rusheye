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

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.jboss.lupic.exception.ParsingException;
import org.jboss.lupic.parser.listener.ParserListener;
import org.jboss.lupic.parser.listener.ParserListenerAdapter;
import org.jboss.lupic.suite.GlobalConfiguration;
import org.jboss.lupic.suite.Test;
import org.jboss.lupic.suite.VisualSuite;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class Parser {

    private Set<ParserListener> listeners = new LinkedHashSet<ParserListener>();
    private Handler handler = new Handler(listeners);

    public Parser() {
        this.registerListener(new ParserListenerRegistrationListener());
    }

    public void parseResource(String resourceName) {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        parseStream(inputStream);
    }

    public void parseFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        parseStream(inputStream);
    }

    public void parseStream(InputStream inputStream) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            
            EventFilter filter = new EventFilter() {
                public boolean accept(XMLEvent event) {
                    return event.isStartElement();
                }
            };
            
            XMLEventReader reader = factory.createXMLEventReader(inputStream);
            XMLEventReader filteredReader = factory.createFilteredReader(reader, filter);
            
            JAXBContext ctx = JAXBContext.newInstance(VisualSuite.class.getPackage().getName());
            Unmarshaller um = ctx.createUnmarshaller();
            
            // skip parsing of the first element - visual-suite
            filteredReader.nextEvent();
            
            while (filteredReader.peek() != null) {
                Object o = um.unmarshal(reader);
                if (o instanceof GlobalConfiguration) {
                    GlobalConfiguration globalConfiguration = (GlobalConfiguration) o;
                    handler.getContext().setCurrentConfiguration(globalConfiguration);
                }
                if (o instanceof Test) {
                    Test test = (Test) o;
                    handler.getContext().setCurrentConfiguration(test);
                    handler.getContext().setCurrentTest(test);
                }
            }
        } catch (XMLStreamException e) {
            throw new ParsingException(e);
        } catch (JAXBException e) {
            throw new ParsingException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
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
