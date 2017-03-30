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
package org.jboss.rusheye.parser;

import java.io.IOException;
import org.dom4j.QName;
import org.jboss.rusheye.exception.ConfigurationValidationException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import static org.jboss.rusheye.parser.VisualSuiteDefinitions.GLOBAL_DIFFERENCE_TRESHOLD;
import static org.jboss.rusheye.parser.VisualSuiteDefinitions.ONE_PIXEL_TRESHOLD;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPerceptionTreshold extends AbstractVisualSuiteDefinitionTest {

    private static final int MAX_PIXEL_TRESHOLD = 767;

    @DataProvider(name = "pixel-treshold-allowed")
    public Object[][] providePixelTresholdAllowed() {
        return new Integer[][] {{0}, {1}, {MAX_PIXEL_TRESHOLD}};
    }

    @DataProvider(name = "pixel-treshold-not-allowed")
    public Object[][] providePixelTresholdNotAllowed() {
        return new Object[][] {{-1}, {"1%"}, {"3px"}, {"a"}};
    }

    @Test(dataProvider = "pixel-treshold-allowed")
    public void testOnePixelTresholdAllowed(Object pixelTreshold) throws SAXException, IOException {
        tryParsePixelTreshold(ONE_PIXEL_TRESHOLD, pixelTreshold);
    }

    @Test(dataProvider = "pixel-treshold-not-allowed", expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "\"[^\"]+\" does not satisfy the \"nonNegativeInteger\" type .*")
    public void testOnePixelTresholdNotAllowed(Object pixelTreshold) throws SAXException, IOException {
        tryParsePixelTreshold(ONE_PIXEL_TRESHOLD, pixelTreshold);
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "Unknown reason .*")
    public void testOnePixelTresholdEmptyNotAllowed() throws SAXException, IOException {
        tryParsePixelTreshold(ONE_PIXEL_TRESHOLD, "");
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "the value is out of the range .*")
    public void testOnePixelTresholdOutOfRangeNotAllowed() throws SAXException, IOException {
        tryParsePixelTreshold(ONE_PIXEL_TRESHOLD, MAX_PIXEL_TRESHOLD + 1);
    }

    @Test(dataProvider = "pixel-treshold-allowed")
    public void testGlobalDifferenceAllowed(Object pixelTreshold) throws SAXException, IOException {
        tryParsePixelTreshold(GLOBAL_DIFFERENCE_TRESHOLD, pixelTreshold);
    }

    @Test(dataProvider = "pixel-treshold-not-allowed", expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "\"[^\"]+\" does not satisfy the \"nonNegativeInteger\" type .*")
    public void testGlobalDifferenceNotAllowed(Object pixelTreshold) throws SAXException, IOException {
        tryParsePixelTreshold(GLOBAL_DIFFERENCE_TRESHOLD, pixelTreshold);
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "Unknown reason .*")
    public void testGlobalDifferenceEmptyNotAllowed() throws SAXException, IOException {
        tryParsePixelTreshold(GLOBAL_DIFFERENCE_TRESHOLD, "");
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "the value is out of the range .*")
    public void testGlobalDifferenceOutOfRangeNotAllowed() throws SAXException, IOException {
        tryParsePixelTreshold(GLOBAL_DIFFERENCE_TRESHOLD, MAX_PIXEL_TRESHOLD + 1);
    }

    private void tryParsePixelTreshold(QName qName, Object pixelTreshold) throws IOException, SAXException {
        stub.perception.addElement(qName).addText(pixelTreshold.toString());

        startWriter();
        parse();
    }
}
