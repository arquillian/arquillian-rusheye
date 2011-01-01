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
package org.jboss.rusheye.parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.FileUtils;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.ri.Stax2FilteredStreamReader;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;
import org.jboss.rusheye.exception.ConfigurationException;
import org.jboss.rusheye.exception.ConfigurationValidationException;
import org.jboss.rusheye.exception.ParsingException;
import org.jboss.rusheye.listener.SuiteListenerAdapter;
import org.jboss.rusheye.listener.SuiteListener;
import org.jboss.rusheye.suite.GlobalConfiguration;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Pattern;
import org.jboss.rusheye.suite.Test;
import org.jboss.rusheye.suite.VisualSuite;

import com.ctc.wstx.exc.WstxParsingException;
import com.ctc.wstx.exc.WstxValidationException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class Parser {

    private Set<SuiteListener> listeners = new LinkedHashSet<SuiteListener>();
    private Handler handler = new Handler(listeners);

    public Parser() {
        this.registerListener(new ParserListenerRegistrationListener());
    }

    public void parseResource(String resourceName) {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        parseStream(inputStream);
    }

    public void parseStream(InputStream inputStream) {
        try {
            File tmp = File.createTempFile(Parser.class.getName(), ".tmp");
            BufferedInputStream in = new BufferedInputStream(inputStream);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmp));

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            parseFileTempFile(tmp);
        } catch (IOException e) {
            throw new ParsingException(e);
        }
    }

    public void parseFile(File file) {
        parseFile(file, false);
    }

    public void parseFileTempFile(File file) {
        parseFile(file, true);
    }

    private void parseFile(File file, boolean tmpfile) {
        VisualSuite visualSuite = null;
        try {
            XMLValidationSchemaFactory schemaFactory = XMLValidationSchemaFactory
                .newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
            URL schemaURL = getClass().getClassLoader().getResource("visual-suite.xsd");
            XMLValidationSchema schema = schemaFactory.createSchema(schemaURL);

            XMLInputFactory2 factory = (XMLInputFactory2) XMLInputFactory.newInstance();

            StreamFilter filter = new StreamFilter() {
                @Override
                public boolean accept(XMLStreamReader reader) {
                    return reader.isStartElement();
                }
            };

            XMLStreamReader2 reader = factory.createXMLStreamReader(file);
            XMLStreamReader2 filteredReader = new Stax2FilteredStreamReader(reader, filter);

            reader.validateAgainst(schema);

            // EventFilter filter = new EventFilter() {
            // @Override
            // public boolean accept(XMLEvent reader) {
            // return reader.isStartElement();
            // }
            // };
            //
            // XMLEventReader reader = factory.createXMLEventReader(file);
            // XMLEventReader filteredReader = factory.createFilteredReader(reader, filter);

            JAXBContext ctx = JAXBContext.newInstance(VisualSuite.class.getPackage().getName());
            Unmarshaller um = ctx.createUnmarshaller();

            UnmarshallerMultiListener listener = new UnmarshallerMultiListener();
            um.setListener(listener);

            // skip parsing of the first element - visual-suite
            filteredReader.nextTag();

            visualSuite = new VisualSuite();
            handler.setVisualSuite(visualSuite);
            handler.getContext().invokeListeners().onSuiteStarted(visualSuite);

            listener.registerListener(new UniqueIdentityChecker(handler.getContext()));

            while (filteredReader.hasNext()) {
                try {
                    // go on the start of the next tag
                    filteredReader.nextTag();

                    Object o = um.unmarshal(reader);
                    if (o instanceof GlobalConfiguration) {
                        GlobalConfiguration globalConfiguration = (GlobalConfiguration) o;
                        handler.getContext().setCurrentConfiguration(globalConfiguration);
                        visualSuite.setGlobalConfiguration(globalConfiguration);
                        handler.getContext().invokeListeners().onConfigurationReady(visualSuite);

                        RetriverInjector retriverInjector = new RetriverInjector(this);
                        for (Mask mask : globalConfiguration.getMasks()) {
                            retriverInjector.afterUnmarshal(mask, null);
                        }
                        listener.registerListener(retriverInjector);
                    }
                    if (o instanceof Test) {
                        Test test = (Test) o;
                        handler.getContext().setCurrentConfiguration(test);
                        handler.getContext().setCurrentTest(test);
                        for (Pattern pattern : test.getPatterns()) {
                            handler.getContext().invokeListeners().onPatternReady(test, pattern);
                        }
                        Test testWrapped = ConfigurationCompiler.wrap(test, visualSuite.getGlobalConfiguration());
                        handler.getContext().invokeListeners().onTestReady(testWrapped);
                    }
                } catch (WstxParsingException e) {
                    // intentionally blank - wrong end of document detection
                }

            }
        } catch (XMLStreamException e) {
            throw handleParsingException(e, e);
        } catch (JAXBException e) {
            throw handleParsingException(e, e.getLinkedException());
        } finally {
            if (visualSuite != null && handler.getContext() != null) {
                handler.getContext().invokeListeners().onSuiteReady(visualSuite);
            }
            if (tmpfile) {
                FileUtils.deleteQuietly(file);
            }
        }
    }

    private RuntimeException handleParsingException(Throwable originalException, Throwable cause) {
        if (cause != null && cause instanceof WstxValidationException) {
            String message = cause.getMessage().replaceAll("\n", "");
            return new ConfigurationValidationException(message, cause);
        }
        return new ParsingException(originalException);
    }

    public void registerListener(SuiteListener parserListener) {
        synchronized (parserListener) {
            if (listeners.contains(parserListener)) {
                return;
            }
            listeners.add(parserListener);
        }
    }

    public void unregisterListener(SuiteListener parserListener) {
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

    private class ParserListenerRegistrationListener extends SuiteListenerAdapter {
        @Override
        public void onConfigurationReady(VisualSuite visualSuite) {
            for (SuiteListener listener : visualSuite.getGlobalConfiguration().getConfiguredListeners()) {
                listener.onSuiteStarted(visualSuite);
                listener.onConfigurationReady(visualSuite);
                Parser.this.registerListener(listener);
            }

            if (listeners.size() == 1) {
                throw new ConfigurationException("No ParserListener was registered to process parsed tests");
            }
        }
    }
}
