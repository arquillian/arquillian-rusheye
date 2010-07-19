package org.jboss.lupic.test.parser;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.jboss.lupic.test.parser.VisualSuiteDefinitions.*;

public class TestPerception extends AbstractVisualSuiteDefinitionTest {

	@DataProvider(name = "one-pixel-treshold-allowed")
	public Object[][] provideOnePixelTresholdAllowe() {
		return new Integer[][] { { 0 }, { 1 }, { 767 } };
	}

	@Test(dataProvider = "one-pixel-treshold-allowed")
	public void testOnePixelTresholdAllowed(int onePixelTreshold)
			throws SAXException, IOException {
		String treshold = String.valueOf(onePixelTreshold);
		stub.perception.addElement(ONE_PIXEL_TRESHOLD).addText(treshold);

		startWriter();
		parse();
	}

	@DataProvider(name = "one-pixel-treshold-not-allowed")
	public Object[][] provideOnePixelTresholdNotAllowe() {
		return new Object[][] { { -1 }, { 768 }, { "1%" }, { "3px" }, { "a" } };
	}

	@Test(dataProvider = "one-pixel-treshold-not-allowed", expectedExceptions = SAXParseException.class)
	public void testOnePixelTresholdNotAllowed(Object onePixelTreshold)
			throws SAXException, IOException {
		stub.perception.addElement(ONE_PIXEL_TRESHOLD).addText(
				onePixelTreshold.toString());

		startWriter();
		parse();
	}
}
