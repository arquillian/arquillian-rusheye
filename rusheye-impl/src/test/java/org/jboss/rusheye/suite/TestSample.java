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
package org.jboss.rusheye.suite;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.fail;

import java.awt.image.BufferedImage;

import org.jboss.rusheye.exception.RetrieverException;
import org.jboss.rusheye.retriever.SampleRetriever;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestSample {
    private static final String TEST_NAME1 = "testName1";
    private static final String TEST_NAME2 = "testName2";

    private static final BufferedImage BUFFERED_IMAGE1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private static final BufferedImage BUFFERED_IMAGE2 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

    private final SampleRetriever sampleRetriever = new SampleRetrieverImpl() {

        @Override
        public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
            if (source.equals(TEST_NAME1)) {
                return BUFFERED_IMAGE1;
            } else if (source.equals(TEST_NAME2)) {
                return BUFFERED_IMAGE2;
            } else {
                throw new AssertionError();
            }
        }
    };

    @Test
    public void testRetrievingTwoImages() {
        Sample sample1 = newSample(TEST_NAME1, sampleRetriever);
        Sample sample2 = newSample(TEST_NAME2, sampleRetriever);

        sample1.run();
        sample2.run();

        try {
            assertSame(sample1.get(), BUFFERED_IMAGE1);
            assertSame(sample2.get(), BUFFERED_IMAGE2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRepeatingRetrieve() {
        Sample sample1 = newSample(TEST_NAME1, sampleRetriever);
        Sample sample2 = newSample(TEST_NAME2, sampleRetriever);

        sample1.run();
        sample2.run();

        try {
            assertSame(sample1.get(), BUFFERED_IMAGE1);
            assertSame(sample2.get(), BUFFERED_IMAGE2);
            assertSame(sample2.get(), BUFFERED_IMAGE2);
            assertSame(sample1.get(), BUFFERED_IMAGE1);
            assertSame(sample1.get(), BUFFERED_IMAGE1);
            assertSame(sample2.get(), BUFFERED_IMAGE2);
        } catch (Exception e) {
            fail();
        }
    }

    private Sample newSample(String source, SampleRetriever sampleRetriever) {
        Sample sample = new Sample();
        sample.setSource(source);
        sample.setSampleRetriever(sampleRetriever);
        return sample;

    }
}
