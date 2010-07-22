package org.jboss.lupic.test.parser;

import java.io.IOException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestImage extends AbstractTestOfTestElement {

	@Test(expectedExceptions = SAXParseException.class)
	public void testEmptySourceRaiseException() throws IOException,
			SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);
		addImage("");

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testDoubleImagesRaiseException() throws IOException,
			SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);
		addImage(null);
		addImage(null);

		startWriter();
		parse();
	}

	@Test
	public void testOneCharacterNameShouldPass() throws IOException,
			SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);
		addImage("1");

		startWriter();
		parse();
	}
}
