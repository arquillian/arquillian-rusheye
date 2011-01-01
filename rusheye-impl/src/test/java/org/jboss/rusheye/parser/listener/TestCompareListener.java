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
package org.jboss.rusheye.parser.listener;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Set;

import org.jboss.rusheye.core.ImageComparator;
import org.jboss.rusheye.result.collector.ResultCollector;
import org.jboss.rusheye.result.collector.ResultCollectorAdapter;
import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Pattern;
import org.jboss.rusheye.suite.Perception;
import org.jboss.rusheye.suite.Properties;
import org.jboss.rusheye.suite.Sample;
import org.jboss.rusheye.suite.VisualSuite;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

public class TestCompareListener {

    private static TestCompareListener parent;

    @Mock
    ImageComparator imageComparator;

    @Spy
    CompareListener compareListener = new CompareListener();

    @Spy
    ResultCollector resultCollector = new OrderTestingResultCollector();
    InOrder collectorOrder;

    @Mock
    VisualSuite visualSuite;

    @Mock
    org.jboss.rusheye.suite.Test test;

    @Mock
    Pattern pattern;
    InOrder patternOrder;

    @Mock
    BufferedImage patternImage;

    @Mock
    Sample sample;
    InOrder sampleOrder;

    @Mock
    BufferedImage sampleImage;

    @Mock
    ComparisonResult comparisonResult;

    @Mock
    Element element;

    @BeforeClass
    public void initMocks() {
        parent = this;
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void beforeMerthod() throws Exception {

        when(
            imageComparator.compare(any(BufferedImage.class), any(BufferedImage.class), any(Perception.class),
                any(Set.class))).thenReturn(comparisonResult);

        Properties properties = new Properties();
        properties.setProperty("result-collector", InvocationPassingResultCollector.class.getName());

        LinkedList<Pattern> patterns = new LinkedList<Pattern>();
        patterns.add(pattern);

        doReturn(sample).when(test).getSample();
        doReturn(patterns).when(test).getPatterns();
        doReturn(sampleImage).when(sample).get();
        doNothing().when(sample).run();
        doReturn(patternImage).when(pattern).get();

        sampleOrder = inOrder(sample);
        patternOrder = inOrder(pattern);
        collectorOrder = inOrder(resultCollector);

        compareListener.setProperties(properties);
        compareListener.imageComparator = imageComparator;
    }

    @Test
    public void testOrder() throws Exception {
        // run
        compareListener.onSuiteStarted(visualSuite);
        compareListener.resultCollector = resultCollector;
        compareListener.onConfigurationParsed(visualSuite);
        compareListener.onPatternParsed(test, pattern);
        compareListener.onTestParsed(test);
        compareListener.onSuiteParsed(visualSuite);

        // order verification
        sampleOrder.verify(sample).run();
        sampleOrder.verify(sample).get();
        patternOrder.verify(pattern).run();
        patternOrder.verify(pattern).get();

        ResultCollector col = collectorOrder.verify(resultCollector);
        col.setProperties(any(Properties.class));
        col.onSuiteStarted(visualSuite);
        col.onConfigurationParsed(visualSuite);
        col.onPatternParsed(test, pattern);
        col.onPatternStarted(pattern);
        col.onTestParsed(test);
        col.onTestStarted(test);
        col.onSampleStarted(test);
        col.onSampleLoaded(test);
        col.onPatternLoaded(test, pattern);
        col.onPatternCompleted(test, pattern, comparisonResult);
        col.onTestCompleted(test);
        col.onSuiteParsed(visualSuite);
        col.onSuiteCompleted(visualSuite);
    }

    public static class OrderTestingResultCollector extends ResultCollectorAdapter {
    }

    public static class InvocationPassingResultCollector extends ResultCollectorAdapter {
        @Override
        public void setProperties(Properties properties) {
            parent.resultCollector.setProperties(properties);
        }

        @Override
        public void onSuiteStarted(VisualSuite visualSuite) {
            parent.resultCollector.onSuiteStarted(visualSuite);
        }
    }
}
