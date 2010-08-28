package org.jboss.lupic.parser;

import java.io.IOException;
import java.util.Properties;

import org.jboss.lupic.retriever.Retriever;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import static org.testng.Assert.*;

public class TestMaskRetriever extends AbstractVisualSuiteDefinitionTest {

	private final static String SOURCE = "source";

	@Test
	public void testPropertiesShouldPass() throws SAXException, IOException {
		String retrieverImpl = TestImageRetriever.AssertingRetriever.class
				.getName();
		stub.maskRetriever.addAttribute("class", retrieverImpl);

		stub.maskRetriever.addElement("xxx").setText("1");
		stub.maskRetriever.addElement("yyy").setText("2");

		startWriter();
		parse();

		Retriever retriever = handler.getVisualSuite().getGlobalConfiguration()
				.getMaskRetriever();
		assertNull(retriever.retrieve(SOURCE, new Properties()));
	}
}
