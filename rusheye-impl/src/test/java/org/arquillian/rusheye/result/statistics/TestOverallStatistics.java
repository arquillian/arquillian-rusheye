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
package org.arquillian.rusheye.result.statistics;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Properties;
import org.arquillian.rusheye.suite.ResultConclusion;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestOverallStatistics {

    OverallStatistics overallStatistics = new OverallStatistics();

    @Mock
    org.arquillian.rusheye.suite.Test test;

    @Mock
    Pattern pattern;

    @Mock
    Properties properties;

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOverallStatistics() throws IOException, InterruptedException, BrokenBarrierException {
        List<String> list = new LinkedList<String>();
        PipedWriter pipedWriter = new PipedWriter();
        CyclicBarrier barrier = new CyclicBarrier(2);

        new Thread(new StreamToListWrapper(pipedWriter, list, barrier)).start();

        when(properties.getProperty("overall-statistics-output")).thenReturn(pipedWriter);
        when(test.getName()).thenReturn("testName");
        when(test.getPatterns()).thenReturn(Arrays.asList(pattern));
        when(pattern.getName()).thenReturn("patternName");
        when(pattern.getConclusion()).thenReturn(ResultConclusion.PERCEPTUALLY_SAME);
        when(pattern.getOutput()).thenReturn("someLocation");

        overallStatistics.setProperties(properties);

        overallStatistics.onPatternCompleted(pattern);

        overallStatistics.onTestCompleted(test);
        barrier.await();
        Assert.assertTrue(list.contains("[ PERCEPTUALLY_SAME ] testName"));

        overallStatistics.onSuiteCompleted();
        barrier.await();
        Assert.assertTrue(list.contains("  Overall Statistics:"));
        Assert.assertTrue(list.contains("  PERCEPTUALLY_SAME: 1"));
    }

    private class StreamToListWrapper implements Runnable {
        BufferedReader reader;
        List<String> list;
        CyclicBarrier barrier;

        public StreamToListWrapper(PipedWriter pipedWriter, List<String> list, CyclicBarrier barrier)
            throws IOException {
            this.reader = new BufferedReader(new PipedReader(pipedWriter));
            this.list = list;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                String outputString;
                while ((outputString = reader.readLine()) != null) {
                    list.add(outputString);
                    while (reader.ready()) {
                        list.add(reader.readLine());
                    }
                    barrier.await();
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } catch (BrokenBarrierException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
