package org.jboss.lupic.parser;

import java.io.IOException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestPattern extends AbstractTestOfTestElement {
    @Test(expectedExceptions = SAXParseException.class)
    public void testNotUniqueNameAcrossTestsShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(PATTERN1_NAME);

        addTest(TEST2_NAME);
        addPattern(PATTERN1_NAME);

        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
    public void testNotUniqueNameInsideOneTestShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern(PATTERN1_NAME);
        addPattern(PATTERN1_NAME);

        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
    public void testEmptyNameShouldRaiseException() throws IOException, SAXException {
        addTest(TEST1_NAME);
        addPattern("");

        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
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
