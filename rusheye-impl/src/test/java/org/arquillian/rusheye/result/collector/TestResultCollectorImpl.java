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
package org.arquillian.rusheye.result.collector;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.arquillian.rusheye.result.ResultStatistics;
import org.arquillian.rusheye.result.ResultStorage;
import org.arquillian.rusheye.result.writer.ResultWriter;
import org.arquillian.rusheye.suite.ComparisonResult;
import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Perception;
import org.arquillian.rusheye.suite.Properties;
import org.arquillian.rusheye.suite.VisualSuite;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestResultCollectorImpl {

    @Mock
    static ResultStatistics statistics;

    @Mock
    static ResultWriter writer;

    @Mock
    static ResultStorage storage;

    @Spy
    ResultCollectorImpl collector = new ResultCollectorImpl();

    @Mock
    VisualSuite visualSuite;

    @Mock
    org.arquillian.rusheye.suite.Test test1;

    @Mock
    org.arquillian.rusheye.suite.Test test2;

    @Spy
    Pattern pattern1 = new Pattern();

    @Spy
    Pattern pattern2 = new Pattern();

    @Mock
    ComparisonResult comparisonResult;

    @Mock
    Perception perception;

    @Mock
    BufferedImage image;

    InOrder order;

    @BeforeMethod
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);

        when(test1.getPerception()).thenReturn(perception);
        when(test1.getPatterns()).thenReturn(Arrays.asList(pattern1));
        when(test2.getPerception()).thenReturn(perception);
        when(test2.getPatterns()).thenReturn(Arrays.asList(pattern2));
        when(perception.getGlobalDifferencePercentage()).thenReturn((short) 5);
        when(perception.getGlobalDifferencePixelAmount()).thenReturn(null);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(100);
        when(comparisonResult.getDifferentPixels()).thenReturn(4);
        when(comparisonResult.getDiffImage()).thenReturn(image);
        when(storage.store(any(org.arquillian.rusheye.suite.Test.class), any(Pattern.class), any(BufferedImage.class)))
            .thenReturn("some-location");
    }

    @Test
    public void testProperties() {
        Properties properties = setupProperties();
        collector.onConfigurationReady(visualSuite);

        verify(statistics, times(1)).setProperties(properties);
        verify(writer, times(1)).setProperties(properties);
        verify(storage, times(1)).setProperties(properties);
    }

    @Test
    public void testPropertEnd() {
        setupProperties();
        collector.onConfigurationReady(visualSuite);
        collector.onSuiteCompleted(visualSuite);

        verify(statistics, times(1)).onSuiteCompleted();
        verify(writer, times(1)).close();
        verify(storage, times(1)).end();
    }

    @Test
    public void testPatternNotNullValues() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();

                Pattern pattern = (Pattern) args[0];

                Assert.assertNotNull(pattern.getConclusion());
                Assert.assertNotNull(pattern.getComparisonResult());
                Assert.assertEquals(pattern.getOutput(), "some-location");

                return null;
            }
        }).when(statistics).onPatternCompleted(any(Pattern.class));

        setupProperties();
        collector.onConfigurationReady(visualSuite);
        collector.onPatternCompleted(test1, pattern1, comparisonResult);
    }

    @Test
    public void testOrder() {
        setupProperties();
        collector.onConfigurationReady(visualSuite);
        collector.onPatternCompleted(test1, pattern1, comparisonResult);
        collector.onPatternCompleted(test1, pattern2, comparisonResult);
        collector.onTestCompleted(test1);
        collector.onPatternCompleted(test2, pattern1, comparisonResult);
        collector.onPatternCompleted(test2, pattern2, comparisonResult);
        collector.onTestCompleted(test2);
        collector.onSuiteCompleted(visualSuite);

        order = inOrder(image, storage, statistics, writer);

        order.verify(storage).store(test1, pattern1, image);
        order.verify(image).flush();
        order.verify(statistics).onPatternCompleted(any(Pattern.class));

        order.verify(storage).store(test1, pattern2, image);
        order.verify(image).flush();
        order.verify(statistics).onPatternCompleted(any(Pattern.class));

        order.verify(writer).write(test1);
        order.verify(statistics).onTestCompleted(test1);

        order.verify(storage).store(test2, pattern1, image);
        order.verify(image).flush();
        order.verify(statistics).onPatternCompleted(any(Pattern.class));

        order.verify(storage).store(test2, pattern2, image);
        order.verify(image).flush();
        order.verify(statistics).onPatternCompleted(any(Pattern.class));

        order.verify(writer).write(test2);
        order.verify(statistics).onTestCompleted(test2);

        order.verify(writer).close();
        order.verify(statistics).onSuiteCompleted();
        order.verify(storage).end();
    }

    private Properties setupProperties() {
        Properties properties = mock(Properties.class);

        when(properties.getProperty("result-statistics")).thenReturn(MockStatistics.class.getName());
        when(properties.getProperty("result-writer")).thenReturn(MockWriter.class.getName());
        when(properties.getProperty("result-storage")).thenReturn(MockStorage.class.getName());

        collector.setProperties(properties);

        return properties;
    }

    public static class MockStatistics implements ResultStatistics {

        @Override
        public void setProperties(Properties properties) {
            statistics.setProperties(properties);
        }

        @Override
        public void onPatternCompleted(Pattern pattern) {
            statistics.onPatternCompleted(pattern);
        }

        @Override
        public void onTestCompleted(org.arquillian.rusheye.suite.Test test) {
            statistics.onTestCompleted(test);
        }

        @Override
        public void onSuiteCompleted() {
            statistics.onSuiteCompleted();
        }
    }

    public static class MockWriter implements ResultWriter {

        @Override
        public void setProperties(Properties properties) {
            writer.setProperties(properties);
        }

        @Override
        public boolean write(org.arquillian.rusheye.suite.Test test) {
            return writer.write(test);
        }

        @Override
        public void close() {
            writer.close();
        }
    }

    public static class MockStorage implements ResultStorage {

        @Override
        public void setProperties(Properties properties) {
            storage.setProperties(properties);
        }

        @Override
        public String store(org.arquillian.rusheye.suite.Test test, Pattern pattern, BufferedImage differenceImage) {
            return storage.store(test, pattern, differenceImage);
        }

        @Override
        public void end() {
            storage.end();
        }
    }
}
