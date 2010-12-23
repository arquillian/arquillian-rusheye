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
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.apache.commons.io.output.TeeOutputStream;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jboss.lupic.PassingSAXErrorHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class AbstractVisualSuiteDefinitionTest {

    VisualSuiteStub stub;
    XMLWriter writer;
    Parser parser;
    Handler handler;
    // InputSource inputSource;
    InputStream inputStream;
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
        // inputSource = new InputSource(in);
        inputStream = in;

        parser = new Parser();

        handler = parser.getHandler();
    }

    @AfterMethod(alwaysRun = true)
    public void printDocumentOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.err.println(generatedDocument);
        }
    }

    private class WriterRunnable implements Runnable {

        XMLWriter writer;
        VisualSuiteStub stub;

        public WriterRunnable(VisualSuiteStub stub, XMLWriter writer) {
            super();
            this.stub = stub;
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                writer.write(stub.document);
                writer.close();
                generatedDocument = new String(documentOutputStream.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startWriter() {
        Thread writerThread = new Thread(new WriterRunnable(stub, writer));
        writerThread.start();
    }

    // FIXME does not throw exceptions 
    public void parse() throws SAXException, IOException {
        parser.parseStream(inputStream);
    }
}
