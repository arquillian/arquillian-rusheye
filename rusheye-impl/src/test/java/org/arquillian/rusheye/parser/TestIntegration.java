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
package org.arquillian.rusheye.parser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import org.dom4j.Element;
import org.arquillian.rusheye.exception.RetrieverException;
import org.arquillian.rusheye.parser.listener.CompareListener;
import org.arquillian.rusheye.result.collector.ResultCollectorImpl;
import org.arquillian.rusheye.result.statistics.OverallStatistics;
import org.arquillian.rusheye.result.storage.ObjectMapStorage;
import org.arquillian.rusheye.result.writer.XmlResultWriter;
import org.arquillian.rusheye.retriever.ResourceRetriever;
import org.arquillian.rusheye.retriever.sample.ResourceSampleRetriever;
import org.arquillian.rusheye.suite.Properties;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import static org.arquillian.rusheye.parser.VisualSuiteDefinitions.PATTERN;
import static org.arquillian.rusheye.parser.VisualSuiteDefinitions.TEST;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestIntegration extends AbstractVisualSuiteDefinitionTest {

    private static final String[] SAMPLES = new String[] {"different", "different-masked", "not-same", "perceptible",
        "real-sample-1", "real-sample-2", "real-sample-3", "real-sample-4", "real-sample-5", "same"};

    @DataProvider
    public Object[][] provideSamples() {
        Object[][] result = new Object[SAMPLES.length][1];
        int i = 0;
        for (String sample : SAMPLES) {
            result[i++][0] = sample;
        }
        return result;
    }

    private void setup() {
        stub.patternRetriever.addAttribute("type", ResourceRetriever.class.getName());
        stub.maskRetriever.addAttribute("type", ResourceRetriever.class.getName());
        stub.sampleRetriever.addAttribute("type", ScreenshotResourceSampleRetriever.class.getName());
        stub.sampleRetriever.addElement(ResourceSampleRetriever.RESOURCE_EXTENSION).addText("");

        stub.defaultListener.addAttribute("type", CompareListener.class.getName());
        stub.defaultListener.addElement("result-collector").addText(ResultCollectorImpl.class.getName());

        stub.defaultListener.addElement("result-storage").addText(ObjectMapStorage.class.getName());
        stub.defaultListener.addElement("result-writer").addText(StdOutXmlResultWriter.class.getName());
        stub.defaultListener.addElement("result-statistics").addText(OverallStatistics.class.getName());

        stub.sampleRetriever.addElement(ResourceSampleRetriever.RESOURCES_LOCATION).addText("image-comparator");
    }

    @Test(dataProvider = "provideSamples")
    public void testSample(String sample) throws IOException, SAXException {
        setup();

        stub.defaultTest.addAttribute("name", sample);

        stub.defaultPattern.addAttribute("name", "pattern");
        stub.defaultPattern.addAttribute("source", "image-comparator/" + sample + "/pattern.png");

        startWriter();
        parse();
    }

    @Test
    public void testAllSamplesAtOnce() throws IOException, SAXException {
        setup();

        stub.visualSuite.remove(stub.defaultTest);

        for (String sample : SAMPLES) {
            Element test = stub.visualSuite.addElement(TEST).addAttribute("name", sample);

            Element pattern = test.addElement(PATTERN);
            pattern.addAttribute("name", sample + "-pattern");
            pattern.addAttribute("source", "image-comparator/" + sample + "/pattern.png");
        }

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

    public static class ScreenshotResourceSampleRetriever extends ResourceSampleRetriever {
        @Override
        public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
            return super.retrieve(source + "/screenshot.png", localProperties);
        }
    }
}
