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
package org.arquillian.rusheye.result.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;
import org.arquillian.rusheye.RushEye;
import org.arquillian.rusheye.suite.Properties;
import org.arquillian.rusheye.suite.Test;
import org.arquillian.rusheye.suite.annotations.VisualSuiteResult;
import org.arquillian.rusheye.suite.utils.NullingProxy;

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

    public boolean write(Test test) {
        if (!tryInitializeWriter()) {
            return false;
        }

        return writeSafely(new WriterContext(test));
    }

    private boolean writeSafely(WriterContext context) {
        tryWriteStartDocument();
        tryWriteTest(context);

        return !writerFailed;
    }

    private void tryWriteStartDocument() {
        if (!writerFailed && !writtenStartDocument) {
            try {
                writer.writeStartDocument("UTF-8", "1.0");
                writer.setDefaultNamespace(RushEye.NAMESPACE_VISUAL_SUITE_RESULT);
                writer.writeStartElement(RushEye.NAMESPACE_VISUAL_SUITE_RESULT, "visual-suite-result");
                writer.writeDefaultNamespace(RushEye.NAMESPACE_VISUAL_SUITE_RESULT);
                writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
                writer.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation",
                    RushEye.NAMESPACE_VISUAL_SUITE_RESULT + " " + RushEye.SCHEMA_LOCATION_VISUAL_SUITE_RESULT);
                writtenStartDocument = true;
            } catch (XMLStreamException e) {
                e.printStackTrace();
                writerFailed = true;
            }
        }
    }

    private void tryWriteTest(WriterContext context) {
        if (!writerFailed) {
            try {
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
        JAXBContext context = JAXBContext.newInstance("org.arquillian.rusheye.suite");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private XMLValidationSchema createXMLValidationSchema() throws XMLStreamException {
        XMLValidationSchemaFactory schemaFactory = XMLValidationSchemaFactory
            .newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
        URL schemaURL = getClass().getClassLoader().getResource("org/arquillian/rusheye/visual-suite-result.xsd");
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
