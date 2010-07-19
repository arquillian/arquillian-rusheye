package org.jboss.lupic.test.parser;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.jboss.lupic.test.parser.VisualSuiteDefinitions.*;

public class TestPerceptionAmount extends AbstractVisualSuiteDefinitionTest {
	private static final long MAX_PIXEL_AMOUNT = Long.MAX_VALUE;

	@DataProvider(name = "pixel-amount-allowed")
	public Object[][] providePixelAmountAllowed() {
		return new Object[][] { { "0%" }, { "1%" }, { "100%" }, { "0px" },
				{ "1px" }, { MAX_PIXEL_AMOUNT + "px" } };
	}

	@DataProvider(name = "pixel-amount-not-allowed")
	public Object[][] providePixelAmountNotAllowed() {
		return new Object[][] { { "-1%" }, { "0.1%" }, { "101%" }, { "-1px" },
				{ "0" }, { "1" }, { "a" }, { "" }, { "null" } };
	}

	@Test(dataProvider = "pixel-amount-allowed")
	public void testGlobalDifferencePixelAmountAllowed(Object pixelAmount)
			throws IOException, SAXException {
		tryParsePixelAmount(pixelAmount);
	}

	@Test(dataProvider = "pixel-amount-not-allowed", expectedExceptions = SAXParseException.class)
	public void testGlobalDifferencePixelAmountNotAllowed(Object pixelAmount)
			throws IOException, SAXException {
		tryParsePixelAmount(pixelAmount);
	}

	private void tryParsePixelAmount(Object pixelAmount) throws IOException,
			SAXException {
		stub.perception.addElement(GLOBAL_DIFFERENCE_PIXEL_AMOUNT).addText(
				pixelAmount.toString());

		startWriter();
		parse();
	}
}
