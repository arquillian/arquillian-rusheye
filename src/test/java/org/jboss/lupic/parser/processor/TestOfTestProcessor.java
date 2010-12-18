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
import static org.mockito.Mockito.when;

import org.jboss.lupic.parser.Context;
import org.jboss.lupic.parser.listener.ParserListener;
import org.jboss.lupic.suite.GlobalConfiguration;
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
public class TestOfTestProcessor {

    private final static String ATTRIBUTE_PREFIX = TestOfTestProcessor.class.getSimpleName();

    @Mock
    Context context;

    @Mock
    VisualSuite visualSuite;

    @Mock
    Properties properties;

    TestProcessor testProcessor = new TestProcessor() {
        protected Context getContext() {
            return TestOfTestProcessor.this.context;
        }

        protected String getAttribute(String localName) {
            return ATTRIBUTE_PREFIX + localName;
        }

        protected VisualSuite getVisualSuite() {
            return TestOfTestProcessor.this.visualSuite;
        }

        protected Properties getProperties() {
            return TestOfTestProcessor.this.properties;
        }
    };

    GlobalConfiguration globalConfiguration = new GlobalConfiguration();

    @Mock
    ParserListener parserListener;

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        when(visualSuite.getGlobalConfiguration()).thenReturn(globalConfiguration);
        when(context.invokeListeners()).thenReturn(parserListener);
    }

    @Test
    public void testPerceptionDefaultValues() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();

                org.jboss.lupic.suite.Test test = (org.jboss.lupic.suite.Test) args[0];

                Assert.assertNotNull(test.getPerception().getOnePixelTreshold());
                Assert.assertNotNull(test.getPerception().getGlobalDifferenceTreshold());
                Assert.assertNotNull(test.getPerception().getGlobalDifferencePixelAmount());
                Assert.assertNull(test.getPerception().getGlobalDifferencePercentage());

                return null;
            }
        }).when(parserListener).onTestParsed(any(org.jboss.lupic.suite.Test.class));

        testProcessor.start();
        testProcessor.end();
    }
}
