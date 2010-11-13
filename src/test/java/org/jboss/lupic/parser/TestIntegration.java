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

import java.io.IOException;
import java.io.OutputStream;

import org.jboss.lupic.parser.listener.CompareListener;
import org.jboss.lupic.result.collector.ResultCollectorImpl;
import org.jboss.lupic.result.statistics.OverallStatistics;
import org.jboss.lupic.result.storage.ObjectMapStorage;
import org.jboss.lupic.result.writer.XmlResultWriter;
import org.jboss.lupic.retriever.ResourceRetriever;
import org.jboss.lupic.retriever.sample.ResourceSampleRetriever;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestIntegration extends AbstractVisualSuiteDefinitionTest {

    @Test
    public void test() throws IOException, SAXException {
        stub.patternRetriever.addAttribute("class", ResourceRetriever.class.getName());
        stub.maskRetriever.addAttribute("class", ResourceRetriever.class.getName());
        stub.sampleRetriever.addAttribute("class", ResourceSampleRetriever.class.getName());
        stub.sampleRetriever.addElement(ResourceSampleRetriever.RESOURCES_LOCATION).addText("image-comparator/same");
        stub.sampleRetriever.addElement(ResourceSampleRetriever.RESOURCE_EXTENSION).addText("png");

        stub.defaultListener.addAttribute("class", CompareListener.class.getName());
        stub.defaultListener.addElement("result-collector").addText(ResultCollectorImpl.class.getName());

        stub.defaultListener.addElement("result-storage").addText(ObjectMapStorage.class.getName());
        stub.defaultListener.addElement("result-writer").addText(StdOutXmlResultWriter.class.getName());
        stub.defaultListener.addElement("result-statistics").addText(OverallStatistics.class.getName());

        stub.defaultPattern.addAttribute("source", "image-comparator/same/pattern.png");
        stub.defaultTest.addAttribute("name", "screenshot");

        startWriter();
        parse();
    }

    public static class StdOutXmlResultWriter extends XmlResultWriter {
        @Override
        protected OutputStream openOutputStream() throws Exception {
            return System.out;
        }

        @Override
        protected void closeOutputStream() throws Exception {
        }
    }
}
