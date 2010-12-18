package org.jboss.lupic.parser;

import java.io.IOException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

public class SimpleParsingTest extends AbstractVisualSuiteDefinitionTest {
    @Test
    public void test() throws SAXException, IOException {
        startWriter();
        parse();
    }
}
