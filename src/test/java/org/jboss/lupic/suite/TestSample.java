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
package org.jboss.lupic.suite;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.fail;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jboss.lupic.retriever.sample.AbstractSampleRetriever;
import org.jboss.lupic.retriever.sample.SampleRetriever;
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

    private final SampleRetriever sampleRetriever = new AbstractSampleRetriever() {

        @Override
        protected BufferedImage loadSource(String source) throws org.jboss.lupic.retriever.RetrieverException {
            if (source.equals(TEST_NAME1)) {
                return BUFFERED_IMAGE1;
            } else if (source.equals(TEST_NAME2)) {
                return BUFFERED_IMAGE2;
            } else {
                throw new AssertionError();
            }
        }

        @Override
        public Set<String> getAllSources() {
            return new HashSet<String>(Arrays.asList(new String[] { TEST_NAME1, TEST_NAME2 }));
        }
    };

    @Test
    public void testRetrievingTwoImages() {
        Sample sample1 = new Sample(TEST_NAME1, sampleRetriever);
        Sample sample2 = new Sample(TEST_NAME2, sampleRetriever);

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
        Sample sample1 = new Sample(TEST_NAME1, sampleRetriever);
        Sample sample2 = new Sample(TEST_NAME2, sampleRetriever);

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
}
