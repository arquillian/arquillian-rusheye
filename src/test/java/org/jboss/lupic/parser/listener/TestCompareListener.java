package org.jboss.lupic.parser.listener;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
import static org.jboss.lupic.parser.listener.TestCompareListener.State.*;

import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.jboss.lupic.core.ComparisonResult;
import org.jboss.lupic.core.ImageComparator;
import org.jboss.lupic.result.ResultCollector;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Perception;
import org.jboss.lupic.suite.Sample;
import org.jboss.lupic.suite.VisualSuite;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestCompareListener {
    @Mock
    ImageComparator imageComparator;

    @Spy
    CompareListener compareListener = new CompareListener();

    @Mock
    VisualSuite visualSuite;

    @Mock
    org.jboss.lupic.suite.Test test;

    @Mock
    Pattern pattern;

    @Mock
    BufferedImage patternImage;

    InOrder patternOrder;

    @Mock
    Sample sample;

    @Mock
    BufferedImage sampleImage;

    InOrder sampleOrder;

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
        properties.setProperty("result-collector", AssertingResultCollector.class.getName());

        LinkedHashSet<Pattern> patterns = new LinkedHashSet<Pattern>();
        patterns.add(pattern);

        doReturn(sample).when(test).getSample();
        doReturn(patterns).when(test).getPatterns();
        doReturn(sampleImage).when(sample).get();
        doNothing().when(sample).run();
        doReturn(patternImage).when(pattern).get();

        sampleOrder = inOrder(sample);
        patternOrder = inOrder(pattern);

        compareListener.setProperties(properties);
        compareListener.imageComparator = imageComparator;
    }

    @Test
    public void testOrdering() throws Exception {
        compareListener.onSuiteStarted(visualSuite);
        compareListener.onConfigurationParsed(visualSuite);
        compareListener.onPatternParsed(test, pattern);
        compareListener.onTestParsed(test);
        compareListener.onSuiteParsed(visualSuite);

        sampleOrder.verify(sample).run();
        sampleOrder.verify(sample).get();
        patternOrder.verify(pattern).run();
        patternOrder.verify(pattern).get();
    }

    static TestCompareListener parent;

    public static enum State {
        SET_PROPERTIES,
        SUITE_STARTED,
        CONFIGURATION_PARSED,
        PATTERN_PARSED,
        PATTERN_STARTED,
        TEST_PARSED,
        TEST_STARTED,
        SAMPLE_STARTED,
        SAMPLE_LOADED,
        PATTERN_LOADED,
        PATTERN_COMPLETED,
        TEST_COMPLETED,
        SUITE_PARSED,
        SUITE_COMPLETED
    }

    public static class AssertingResultCollector implements ResultCollector {

        State state = null;

        public void setProperties(Properties properties) {
            assertState(SET_PROPERTIES);
        }

        public void onSuiteStarted(VisualSuite visualSuite) {
            assertState(SUITE_STARTED);
        }

        public void onConfigurationParsed(VisualSuite visualSuite) {
            assertState(CONFIGURATION_PARSED);
        }

        public void onTestStarted(org.jboss.lupic.suite.Test test) {
            assertState(TEST_STARTED);
        }

        public void onSampleStarted(org.jboss.lupic.suite.Test test) {
            assertState(SAMPLE_STARTED);
        }

        public void onSampleLoaded(org.jboss.lupic.suite.Test test) {
            assertState(SAMPLE_LOADED);
        }

        public void onPatternParsed(Configuration configuration, Pattern pattern) {
            assertState(PATTERN_PARSED);
        }

        public void onPatternStarted(Pattern pattern) {
            assertState(PATTERN_STARTED);
        }

        public void onPatternLoaded(org.jboss.lupic.suite.Test test, Pattern pattern) {
            assertState(PATTERN_LOADED);
        }

        public void onPatternCompleted(org.jboss.lupic.suite.Test test, Pattern pattern,
            ComparisonResult comparisonResult) {
            assertState(PATTERN_COMPLETED);
        }

        public void onTestParsed(org.jboss.lupic.suite.Test test) {
            assertState(TEST_PARSED);
        }

        public void onTestCompleted(org.jboss.lupic.suite.Test test) {
            assertState(TEST_COMPLETED);
        }

        public void onSuiteParsed(VisualSuite visualSuite) {
            assertState(SUITE_PARSED);
        }

        public void onSuiteCompleted(VisualSuite visualSuite) {
            assertState(SUITE_COMPLETED);
        }

        public void assertState(State expectedState) {
            assertSame(incrementAndGetState(), expectedState);
        }

        public State incrementAndGetState() {
            if (state == null) {
                state = SET_PROPERTIES;
            } else if (State.values().length == state.ordinal()) {
                state = null;
            } else {
                state = State.values()[state.ordinal() + 1];
            }
            return state;
        }
    }

}
