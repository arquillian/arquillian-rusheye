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
package org.arquillian.rusheye.parser;

import java.io.IOException;
import org.arquillian.rusheye.exception.ConfigurationValidationException;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPattern extends AbstractTestOfTestElement {

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "pattern's \"name\" attribute have to be unique across suite")
    public void testNotUniqueNameAcrossTestsShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(PATTERN1_NAME);

        addTest(TEST2_NAME);
        addPattern(PATTERN1_NAME);

        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "pattern's \"name\" attribute have to be unique across suite")
    public void testNotUniqueNameInsideOneTestShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(PATTERN1_NAME);
        addPattern(PATTERN1_NAME);

        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "attribute \"name\" has a bad value: \"\" does not satisfy the \"Name\" type.*")
    public void testEmptyNameShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern("");

        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "element \"pattern\" is missing \"name\" attribute.*")
    public void testWithoutNameShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(null);

        startWriter();
        parse();
    }

    @Test
    public void testTwoPatternsShouldPass() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(PATTERN1_NAME);
        addPattern(PATTERN2_NAME);

        startWriter();
        parse();
    }
}
