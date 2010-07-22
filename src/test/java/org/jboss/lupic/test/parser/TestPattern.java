package org.jboss.lupic.test.parser;

import java.io.IOException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestPattern extends AbstractTestOfTestElement {
	@Test(expectedExceptions = SAXParseException.class)
	public void testNotUniqueNameAcrossTestsShouldRaiseException()
			throws IOException, SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);
		addImage(null);

		addTest(TEST2_NAME);
		addPattern(PATTERN1_NAME);
		addImage(null);

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testNotUniqueNameInsideOneTestShouldRaiseException()
			throws IOException, SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);
		addImage(null);
		addPattern(PATTERN1_NAME);
		addImage(null);

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testEmptyNameShouldRaiseException() throws IOException,
			SAXException {
		addTest(TEST1_NAME);
		addPattern("");
		addImage(null);

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testWithoutNameShouldRaiseException() throws IOException,
			SAXException {
		addTest(TEST1_NAME);
		addPattern(null);
		addImage(null);

		startWriter();
		parse();
	}

	@Test
	public void testTwoPatternsShouldPass() throws IOException, SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);
		addImage(null);
		addPattern(PATTERN2_NAME);
		addImage(null);

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testNoImageRaiseException() throws IOException, SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);

		startWriter();
		parse();
	}
}
