package org.jboss.lupic.parser.listener;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.jboss.lupic.core.ComparisonResult;
import org.jboss.lupic.core.ImageComparator;
import org.jboss.lupic.result.collector.ResultCollector;
import org.jboss.lupic.result.collector.ResultCollectorAdapter;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Perception;
import org.jboss.lupic.suite.Sample;
import org.jboss.lupic.suite.VisualSuite;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestCompareListener {
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
    org.jboss.lupic.suite.Test test;

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

        LinkedHashSet<Pattern> patterns = new LinkedHashSet<Pattern>();
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

    static TestCompareListener parent;

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
