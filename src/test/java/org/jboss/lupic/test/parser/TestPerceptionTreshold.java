package org.jboss.lupic.test.parser;

import java.io.IOException;

import org.dom4j.QName;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.jboss.lupic.test.parser.VisualSuiteDefinitions.*;

public class TestPerceptionTreshold extends AbstractVisualSuiteDefinitionTest {

	private static final int MAX_PIXEL_TRESHOLD = 767;

	@DataProvider(name = "pixel-treshold-allowed")
	public Object[][] providePixelTresholdAllowed() {
		return new Integer[][] { { 0 }, { 1 }, { MAX_PIXEL_TRESHOLD } };
	}

	@DataProvider(name = "pixel-treshold-not-allowed")
	public Object[][] providePixelTresholdNotAllowed() {
		return new Object[][] { { -1 }, { MAX_PIXEL_TRESHOLD + 1 }, { "1%" },
				{ "3px" }, { "a" }, { "" } };
	}

	@Test(dataProvider = "pixel-treshold-allowed")
	public void testOnePixelTresholdAllowed(Object pixelTreshold)
			throws SAXException, IOException {
		tryParsePixelTreshold(ONE_PIXEL_TRESHOLD, pixelTreshold);
	}

	@Test(dataProvider = "pixel-treshold-not-allowed", expectedExceptions = SAXParseException.class)
	public void testOnePixelTresholdNotAllowed(Object pixelTreshold)
			throws SAXException, IOException {
		tryParsePixelTreshold(ONE_PIXEL_TRESHOLD, pixelTreshold);
	}

	@Test(dataProvider = "pixel-treshold-allowed")
	public void testGlobalDifferenceAllowed(Object pixelTreshold)
			throws SAXException, IOException {
		tryParsePixelTreshold(GLOBAL_DIFFERENCE_TRESHOLD, pixelTreshold);
	}

	@Test(dataProvider = "pixel-treshold-not-allowed", expectedExceptions = SAXParseException.class)
	public void testGlobalDifferenceNotAllowed(Object pixelTreshold)
			throws SAXException, IOException {
		tryParsePixelTreshold(GLOBAL_DIFFERENCE_TRESHOLD, pixelTreshold);
	}

	private void tryParsePixelTreshold(QName qName, Object pixelTreshold)
			throws IOException, SAXException {
		stub.perception.addElement(qName).addText(pixelTreshold.toString());

		startWriter();
		parse();
	}
}
