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
package org.jboss.lupic.result.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jboss.lupic.result.ResultDetail;
import org.jboss.lupic.result.writer.spooler.SpoolerContext;
import org.jboss.lupic.result.writer.spooler.TestSpooler;
import org.jboss.lupic.suite.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class XmlResultWriter implements ResultWriter {

    protected Properties properties;
    private XMLStreamWriter writer;
    private OutputStream out;
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
                writer.writeStartDocument();
                writer.writeStartElement("visual-suite-result");
                writtenStartDocument = true;
            } catch (XMLStreamException e) {
                writerFailed = true;
            }
        }
    }

    private void tryWriteTest(SpoolerContext context) {
        if (!writerFailed) {
            try {
                new TestSpooler().write(writer, context);
            } catch (XMLStreamException e) {
                writerFailed = true;
            }
        }
    }

    private boolean tryInitializeWriter() {
        if (!writerFailedToInitialize && out == null && writer == null) {
            try {
                out = openOutputStream();
            } catch (Exception e) {
                writerFailedToInitialize = true;
            }

            try {
                writer = createXMLStreamWriter();
            } catch (XMLStreamException e) {
                writerFailedToInitialize = true;
                try {
                    out.close();
                } catch (IOException ioe) {
                    // not need to close
                }
            }
        }

        return !writerFailedToInitialize;
    }

    private XMLStreamWriter createXMLStreamWriter() throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        return factory.createXMLStreamWriter(out);
    }

    protected abstract OutputStream openOutputStream() throws Exception;

    protected abstract void closeOutputStream() throws Exception;

    public void close() {
        try {
            writer.writeEndElement();
            writer.close();
        } catch (XMLStreamException e) {
            // needs to be logged
        }

        try {
            closeOutputStream();
        } catch (Exception e) {
            // needs to be logged
        }

    }

}
