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
package org.jboss.lupic.parser.processor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jboss.lupic.parser.Context;
import org.jboss.lupic.parser.listener.ParserListener;
import org.jboss.lupic.retriever.PatternRetriever;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.GlobalConfiguration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Perception;
import org.jboss.lupic.suite.Properties;
import org.jboss.lupic.suite.VisualSuite;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPatternProcessor {

    private final static String ATTRIBUTE_PREFIX = TestPatternProcessor.class.getSimpleName();

    @Mock
    Context context;

    @Mock
    VisualSuite visualSuite;

    @Mock
    Properties properties;

    PatternProcessor patternProcessor = new PatternProcessor() {
        protected Context getContext() {
            return TestPatternProcessor.this.context;
        }

        protected String getAttribute(String localName) {
            return ATTRIBUTE_PREFIX + localName;
        }

        protected VisualSuite getVisualSuite() {
            return TestPatternProcessor.this.visualSuite;
        }

        protected Properties getProperties() {
            return TestPatternProcessor.this.properties;
        }
    };

    @Mock
    org.jboss.lupic.suite.Test test;

    GlobalConfiguration globalConfiguration = new GlobalConfiguration();

    @Mock
    PatternRetriever patternRetriever;

    @Mock
    List<Pattern> patterns;

    @Mock
    ParserListener parserListener;

    Perception perception = new Perception();

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        when(visualSuite.getGlobalConfiguration()).thenReturn(globalConfiguration);
        when(context.getCurrentTest()).thenReturn(test);
        when(test.getPerception()).thenReturn(perception);
        when(test.getPatterns()).thenReturn(patterns);
        when(context.invokeListeners()).thenReturn(parserListener);
    }

    @Test
    public void testPatternAddedToTest() {
        patternProcessor.end();

        verify(patterns).add(any(Pattern.class));
    }

    @Test
    public void testPatternConfigurationDefaultValues() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();

                Configuration configuration = (Configuration) args[0];

                Assert.assertNotNull(configuration.getPerception().getOnePixelTreshold());
                Assert.assertNotNull(configuration.getPerception().getGlobalDifferenceTreshold());
                Assert.assertNotNull(configuration.getPerception().getGlobalDifferencePixelAmount());
                Assert.assertNull(configuration.getPerception().getGlobalDifferencePercentage());

                return null;
            }
        }).when(parserListener).onPatternParsed(any(Configuration.class), any(Pattern.class));

        patternProcessor.end();
    }
}
