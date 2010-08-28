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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.apache.commons.io.output.TeeOutputStream;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.testng.annotations.BeforeMethod;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class AbstractVisualSuiteDefinitionTest {

    String validationFeature = "http://xml.org/sax/features/validation";
    String schemaFeature = "http://apache.org/xml/features/validation/schema";
    String schemaFullChecking = "http://apache.org/xml/features/validation/schema-full-checking";

    VisualSuiteStub stub;
    XMLWriter writer;
    XMLReader reader;
    Handler handler;
    InputSource inputSource;
    String generatedDocument;
    ByteArrayOutputStream documentOutputStream;

    @BeforeMethod
    public void prepareEnvironment() throws IOException, SAXException {
        stub = new VisualSuiteStub();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream writerOut = new PipedOutputStream(in);
        documentOutputStream = new ByteArrayOutputStream();
        TeeOutputStream out = new TeeOutputStream(writerOut, documentOutputStream);

        OutputFormat format = new OutputFormat("\t", true);
        writer = new XMLWriter(out, format);
        inputSource = new InputSource(in);

        reader = XMLReaderFactory.createXMLReader();
        reader.setFeature(validationFeature, true);
        reader.setFeature(schemaFeature, true);
        reader.setFeature(schemaFullChecking, true);
        reader.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException e) throws SAXException {
                throw e;
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXException {
                throw e;
            }

            @Override
            public void error(SAXParseException e) throws SAXException {
                throw e;
            }
        });

        handler = new Handler();
    }

    private class WriterRunnable implements Runnable {
        @Override
        public void run() {
            try {
                AbstractVisualSuiteDefinitionTest.this.writer
                    .write(AbstractVisualSuiteDefinitionTest.this.stub.document);
                AbstractVisualSuiteDefinitionTest.this.writer.close();
                generatedDocument = new String(documentOutputStream.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startWriter() {
        Thread writerThread = new Thread(new WriterRunnable());
        writerThread.start();
    }

    public void parse() throws IOException, SAXException {
        reader.setContentHandler(handler);
        reader.parse(inputSource);
    }
}
