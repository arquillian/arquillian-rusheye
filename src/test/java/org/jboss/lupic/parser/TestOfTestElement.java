package org.jboss.lupic.parser;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestOfTestElement extends AbstractTestOfTestElement {
	@BeforeMethod
	public void removeDefaultTestElement() {
		stub.visualSuite.remove(stub.defaultTest);
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testNotUniqueNameShouldRaiseException() throws IOException,
			SAXException {
		addTest(TEST1_NAME);
		addPattern(PATTERN1_NAME);

		addTest(TEST1_NAME);
		addPattern(PATTERN2_NAME);

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testWithoutNameShouldRaiseException() throws IOException,
			SAXException {
		addPattern(PATTERN1_NAME);

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testEmptyNameShouldRaiseException() throws IOException,
			SAXException {
		addTest("");
		addPattern(PATTERN1_NAME);

		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
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
