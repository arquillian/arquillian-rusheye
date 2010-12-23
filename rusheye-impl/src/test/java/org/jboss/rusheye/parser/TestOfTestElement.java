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
package org.jboss.rusheye.parser;

import java.io.IOException;

import org.jboss.rusheye.exception.ConfigurationValidationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestOfTestElement extends AbstractTestOfTestElement {
    @BeforeMethod
    public void removeDefaultTestElement() {
        stub.visualSuite.remove(stub.defaultTest);
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "test's \"name\" attribute have to be unique across suite")
    public void testNotUniqueNameShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(PATTERN1_NAME);

        addTest(TEST1_NAME);
        addPattern(PATTERN2_NAME);

        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "element \"test\" is missing \"name\" attribute .*")
    public void testWithoutNameShouldRaiseException() throws IOException, SAXException {
        addTest(null);
        addPattern(PATTERN1_NAME);

        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "attribute \"name\" has a bad value: \"\" does not satisfy the \"Name\" type .*")
    public void testEmptyNameShouldRaiseException() throws IOException, SAXException {
        addTest("");
        addPattern(PATTERN1_NAME);

        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "uncompleted content model. expecting: <mask>,<pattern>,<perception> .*")
    public void testNoPatternRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);

        startWriter();
        parse();
    }

    @Test
    public void testTwoTestsShouldPass() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(PATTERN1_NAME);

        addTest(TEST2_NAME);
        addPattern(PATTERN2_NAME);

        startWriter();
        parse();
    }
}
