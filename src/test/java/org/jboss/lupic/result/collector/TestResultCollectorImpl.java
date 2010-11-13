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
package org.jboss.lupic.result.collector;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

import org.jboss.lupic.core.ComparisonResult;
import org.jboss.lupic.result.ResultDetail;
import org.jboss.lupic.result.statistics.ResultStatistics;
import org.jboss.lupic.result.storage.ResultStorage;
import org.jboss.lupic.result.writer.ResultWriter;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Perception;
import org.jboss.lupic.suite.VisualSuite;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestResultCollectorImpl {

    @Spy
    ResultCollectorImpl collector = new ResultCollectorImpl();

    @Mock
    static ResultStatistics statistics;

    @Mock
    static ResultWriter writer;

    @Mock
    static ResultStorage storage;

    @Mock
    VisualSuite visualSuite;

    @Mock
    org.jboss.lupic.suite.Test test1;

    @Mock
    org.jboss.lupic.suite.Test test2;

    @Mock
    Pattern pattern1;

    @Mock
    Pattern pattern2;

    @Mock
    ComparisonResult comparisonResult;

    @BeforeMethod
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProperties() {
        Properties properties = setupProperties();
        collector.onConfigurationParsed(visualSuite);

        verify(statistics, times(1)).setProperties(properties);
        verify(writer, times(1)).setProperties(properties);
        verify(storage, times(1)).setProperties(properties);
    }

    InOrder order;

    @Test
    public void testPropertEnd() {
        setupProperties();
        collector.onConfigurationParsed(visualSuite);
        collector.onSuiteCompleted(visualSuite);

        verify(statistics, times(1)).onSuiteCompleted();
        verify(writer, times(1)).close();
        verify(storage, times(1)).end();
    }

    @Test
    public void testOrder() {
        Perception perception = mock(Perception.class);
        BufferedImage image = mock(BufferedImage.class);
        List<ResultDetail> resultDetails = mock(List.class);
        ConcurrentMap<org.jboss.lupic.suite.Test, List<ResultDetail>> map = mock(ConcurrentMap.class);
        when(test1.getPerception()).thenReturn(perception);
        when(test2.getPerception()).thenReturn(perception);
        when(perception.getGlobalDifferencePercentage()).thenReturn((short) 5);
        when(perception.getGlobalDifferencePixelAmount()).thenReturn(null);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(100);
        when(comparisonResult.getDifferentPixels()).thenReturn(4);
        when(comparisonResult.getDiffImage()).thenReturn(image);
        when(map.get(any(org.jboss.lupic.suite.Test.class))).thenReturn(resultDetails);

        collector.details = map;
        setupProperties();
        collector.onConfigurationParsed(visualSuite);
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
        order.verify(statistics).onPatternCompleted(any(ResultDetail.class));

        order.verify(storage).store(test1, pattern2, image);
        order.verify(image).flush();
        order.verify(statistics).onPatternCompleted(any(ResultDetail.class));

        order.verify(writer).write(test1, resultDetails);
        order.verify(statistics).onTestCompleted(test1, resultDetails);

        order.verify(storage).store(test2, pattern1, image);
        order.verify(image).flush();
        order.verify(statistics).onPatternCompleted(any(ResultDetail.class));

        order.verify(storage).store(test2, pattern2, image);
        order.verify(image).flush();
        order.verify(statistics).onPatternCompleted(any(ResultDetail.class));

        order.verify(writer).write(test2, resultDetails);
        order.verify(statistics).onTestCompleted(test2, resultDetails);

        order.verify(writer).close();
        order.verify(statistics).onSuiteCompleted();
        order.verify(storage).end();
    }

    private Properties setupProperties() {
        Properties properties = mock(Properties.class);

        when(properties.get("result-statistics")).thenReturn(MockStatistics.class.getName());
        when(properties.get("result-writer")).thenReturn(MockWriter.class.getName());
        when(properties.get("result-storage")).thenReturn(MockStorage.class.getName());

        collector.setProperties(properties);

        return properties;
    }

    public static class MockStatistics implements ResultStatistics {

        @Override
        public void setProperties(Properties properties) {
            statistics.setProperties(properties);
        }

        @Override
        public void onPatternCompleted(ResultDetail detail) {
            statistics.onPatternCompleted(detail);
        }

        @Override
        public void onTestCompleted(org.jboss.lupic.suite.Test test, List<ResultDetail> detail) {
            statistics.onTestCompleted(test, detail);
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
        public boolean write(org.jboss.lupic.suite.Test test, List<ResultDetail> details) {
            return writer.write(test, details);
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
        public String store(org.jboss.lupic.suite.Test test, Pattern pattern, BufferedImage differenceImage) {
            return storage.store(test, pattern, differenceImage);
        }

        @Override
        public void end() {
            storage.end();
        }
    }
}
