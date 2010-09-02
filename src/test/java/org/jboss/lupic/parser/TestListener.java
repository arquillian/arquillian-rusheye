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

import java.io.IOException;

import org.dom4j.Element;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.VisualSuite;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.jboss.lupic.parser.VisualSuiteDefinitions.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestListener extends AbstractVisualSuiteDefinitionTest {

    @Test(expectedExceptions = { IllegalStateException.class }, expectedExceptionsMessageRegExp = "No ParserListener was registered to process parsed tests")
    public void testNoListeners() throws IOException, SAXException {
        stub.globalConfiguration.remove(stub.listeners);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = { SAXParseException.class }, expectedExceptionsMessageRegExp = ".* The content of element 'listeners' is not complete. .*")
    public void testEmptyListeners() throws IOException, SAXException {
        stub.listeners.remove(stub.defaultListener);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "The configured ParserListener class was not found")
    public void testListenerClassNotOnClassPath() throws IOException, SAXException {
        stub.listeners.remove(stub.defaultListener);
        Element listener = stub.listeners.addElement(LISTENER);
        listener.addAttribute("class", "non.existent.Class");
        startWriter();
        parse();
    }

    @Test(expectedExceptions = UnsupportedOperationException.class, expectedExceptionsMessageRegExp = "for assertion purposes")
    public void testListenerCreation() throws IOException, SAXException {
        stub.listeners.remove(stub.defaultListener);
        Element listener = stub.listeners.addElement(LISTENER);
        listener.addAttribute("class", ExceptionThrowingListener.class.getName());
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
        stub.listeners.remove(stub.defaultListener);
        Element listener = stub.listeners.addElement(LISTENER);
        listener.addAttribute("class", AssertingListener.class.getName());
        startWriter();
        parse();
        assertEquals(AssertingListener.state, 5);
    }

    public static class AssertingListener extends ParserListenerAdapter {

        static int state = 0;

        @Override
        public void suiteStarted(VisualSuite visualSuite) {
            assertTrue(state < 1);
            state = 1;
        }

        @Override
        public void configurationParsed(VisualSuite visualSuite) {
            assertTrue(state < 2);
            state = 2;
        }

        @Override
        public void patternParsed(Configuration configuration, Pattern pattern) {
            assertTrue(state < 3);
            state = 3;
        }

        @Override
        public void testParsed(org.jboss.lupic.suite.Test test) {
            assertTrue(state < 4);
            state = 4;
        }

        @Override
        public void suiteCompleted(VisualSuite visualSuite) {
            assertTrue(state < 5);
            state = 5;
        }
    }

    @Test
    public void testListenerProperties() throws IOException, SAXException {
        stub.listeners.remove(stub.defaultListener);
        Element listener = stub.listeners.addElement(LISTENER);
        listener.addAttribute("class", PropertiesCheckingListener.class.getName());

        Element xyz = listener.addElement("xyz");
        xyz.addText("abc");

        startWriter();
        parse();
    }

    public static class PropertiesCheckingListener extends ParserListenerAdapter {
        @Override
        public void suiteStarted(VisualSuite visualSuite) {
            assertEquals(properties.size(), 1);
            assertEquals(properties.get("xyz"), "abc");
        }
    }
}
