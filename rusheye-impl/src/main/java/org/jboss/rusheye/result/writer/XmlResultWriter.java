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
package org.jboss.rusheye.result.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;
import org.jboss.rusheye.result.ResultDetail;
import org.jboss.rusheye.result.writer.spooler.SpoolerContext;
import org.jboss.rusheye.suite.Properties;
import org.jboss.rusheye.suite.Test;
import org.jboss.rusheye.suite.utils.NullingProxy;
import org.jboss.rusheye.suite.utils.VisualSuiteResult;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class XmlResultWriter implements ResultWriter {

    protected Properties properties;
    private XMLStreamWriter writer;
    private OutputStream out;
    private Marshaller marshaller;
    private boolean writerFailedToInitialize = false;
    private boolean writerFailed = false;
    private boolean writtenStartDocument = false;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public boolean write(Test test, List<ResultDetail> details) {
        if (!tryInitializeWriter()) {
            return false;
        }

        return writeSafely(new SpoolerContext(test, details));
    }

    private boolean writeSafely(SpoolerContext context) {
        tryWriteStartDocument();
        tryWriteTest(context);

        return !writerFailed;
    }

    private void tryWriteStartDocument() {
        if (!writerFailed && !writtenStartDocument) {
            try {
                writer.writeStartDocument("UTF-8", "1.0");
                writer.setDefaultNamespace("http://www.jboss.org/test/visual-suite-result");
                writer.writeStartElement("http://www.jboss.org/test/visual-suite-result", "visual-suite-result");
                writer.writeDefaultNamespace("http://www.jboss.org/test/visual-suite-result");
                writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
                writer.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation",
                    "http://www.jboss.org/test/visual-suite-result src/main/resources/visual-suite-result.xsd");
                writtenStartDocument = true;
            } catch (XMLStreamException e) {
                e.printStackTrace();
                writerFailed = true;
            }
        }
    }

    private void tryWriteTest(SpoolerContext context) {
        if (!writerFailed) {
            try {
                fulfilTest(context);
                Test test = context.getTest();
                test = NullingProxy.handle(test, VisualSuiteResult.class);
                marshaller.marshal(test, writer);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
                writerFailed = true;
            }
        }
    }

    private void fulfilTest(SpoolerContext context) {
        while (context.hasNextDetail()) {
            ResultDetail detail = context.getNextDetail();
            detail.getPattern().setComparisonResult(detail.getComparisonResult());
            detail.getPattern().setOutput(detail.getLocation());
            detail.getPattern().setConclusion(detail.getConclusion());
        }
    }

    private boolean tryInitializeWriter() {
        if (!writerFailedToInitialize && out == null && writer == null) {
            try {
                out = openOutputStream();
            } catch (Exception e) {
                e.printStackTrace();
                writerFailedToInitialize = true;
            }

            try {
                writer = createXMLStreamWriter();
                marshaller = createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            } catch (Exception e) {
                e.printStackTrace();
                writerFailedToInitialize = true;
                try {
                    out.close();
                } catch (IOException ioe) {
                    // no need to close
                }
            }
        }

        return !writerFailedToInitialize;
    }

    private XMLStreamWriter createXMLStreamWriter() throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter2 writer = (XMLStreamWriter2) factory.createXMLStreamWriter(out);
        writer.validateAgainst(createXMLValidationSchema());
        // return writer;
        return PrettyXMLStreamWriter.pretty(writer);
    }

    private Marshaller createMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("org.jboss.rusheye.suite");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private XMLValidationSchema createXMLValidationSchema() throws XMLStreamException {
        XMLValidationSchemaFactory schemaFactory = XMLValidationSchemaFactory
            .newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
        URL schemaURL = getClass().getClassLoader().getResource("visual-suite-result.xsd");
        XMLValidationSchema schema = schemaFactory.createSchema(schemaURL);
        return schema;
    }

    protected abstract OutputStream openOutputStream() throws Exception;

    protected abstract void closeOutputStream() throws Exception;

    public void close() {
        try {
            if (writer != null) {
                writer.flush();
                writer.writeEndElement();
                writer.close();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            // needs to be logged
        }

        try {
            closeOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            // needs to be logged
        }

    }

}
