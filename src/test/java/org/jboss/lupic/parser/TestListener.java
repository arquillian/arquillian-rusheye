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
package org.jboss.lupic.parser;

import static org.jboss.lupic.parser.VisualSuiteDefinitions.LISTENER;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.dom4j.Element;
import org.jboss.lupic.exception.ConfigurationValidationException;
import org.jboss.lupic.exception.LupicConfigurationException;
import org.jboss.lupic.parser.listener.ParserListenerAdapter;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.VisualSuite;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestListener extends AbstractVisualSuiteDefinitionTest {

    @Test(expectedExceptions = { ConfigurationValidationException.class }, expectedExceptionsMessageRegExp = "tag name \"pattern-retriever\" is not allowed. Possible tag names are: .*")
    public void testNoListener() throws IOException, SAXException {
        stub.globalConfiguration.remove(stub.defaultListener);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = LupicConfigurationException.class, expectedExceptionsMessageRegExp = "Error when trying to create instance of class 'non.existent.Class'")
    public void testListenerClassNotOnClassPath() throws IOException, SAXException {
        stub.globalConfiguration.remove(stub.defaultListener);
        Element listener = stub.globalConfiguration.addElement(LISTENER);
        listener.addAttribute("type", "non.existent.Class");
        reoderElements();
        startWriter();
        parse();
    }

    @Test(expectedExceptions = UnsupportedOperationException.class, expectedExceptionsMessageRegExp = "for assertion purposes")
    public void testListenerCreation() throws IOException, SAXException {
        stub.globalConfiguration.remove(stub.defaultListener);
        Element listener = stub.globalConfiguration.addElement(LISTENER);
        listener.addAttribute("type", ExceptionThrowingListener.class.getName());
        reoderElements();
        startWriter();
        parse();
    }

    public static class ExceptionThrowingListener extends ParserListenerAdapter {
        public ExceptionThrowingListener() {
            throw new UnsupportedOperationException("for assertion purposes");
        }
    }

    @Test
    public void testAssertingEventOrder() throws IOException, SAXException {
        stub.globalConfiguration.remove(stub.defaultListener);
        Element listener = stub.globalConfiguration.addElement(LISTENER);
        listener.addAttribute("type", AssertingListener.class.getName());
        reoderElements();

        startWriter();
        parse();
        assertEquals(AssertingListener.state, 5);
    }

    public static class AssertingListener extends ParserListenerAdapter {

        static int state = 0;

        @Override
        public void onSuiteStarted(VisualSuite visualSuite) {
            assertTrue(state < 1);
            state = 1;
        }

        @Override
        public void onConfigurationParsed(VisualSuite visualSuite) {
            assertTrue(state < 2);
            state = 2;
        }

        @Override
        public void onPatternParsed(Configuration configuration, Pattern pattern) {
            assertTrue(state < 3);
            state = 3;
        }

        @Override
        public void onTestParsed(org.jboss.lupic.suite.Test test) {
            assertTrue(state < 4);
            state = 4;
        }

        @Override
        public void onSuiteParsed(VisualSuite visualSuite) {
            assertTrue(state < 5);
            state = 5;
        }
    }

    @Test
    public void testListenerProperties() throws IOException, SAXException {
        stub.globalConfiguration.remove(stub.defaultListener);
        Element listener = stub.globalConfiguration.addElement(LISTENER);
        listener.addAttribute("type", PropertiesCheckingListener.class.getName());
        reoderElements();

        Element xyz = listener.addElement("xyz");
        xyz.addText("abc");

        startWriter();
        parse();
    }

    public static class PropertiesCheckingListener extends ParserListenerAdapter {
        @Override
        public void onSuiteStarted(VisualSuite visualSuite) {
            assertEquals(properties.size(), 1);
            assertEquals(properties.getProperty("xyz"), "abc");
        }
    }

    private void reoderElements() {
        stub.globalConfiguration.remove(stub.patternRetriever);
        stub.globalConfiguration.remove(stub.maskRetriever);
        stub.globalConfiguration.remove(stub.sampleRetriever);
        stub.globalConfiguration.remove(stub.perception);

        stub.globalConfiguration.add(stub.patternRetriever);
        stub.globalConfiguration.add(stub.maskRetriever);
        stub.globalConfiguration.add(stub.sampleRetriever);
        stub.globalConfiguration.add(stub.perception);
    }
}
