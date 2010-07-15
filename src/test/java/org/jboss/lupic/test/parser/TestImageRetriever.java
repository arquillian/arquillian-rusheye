package org.jboss.lupic.test.parser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import org.jboss.lupic.retriever.AbstractRetriever;
import org.jboss.lupic.retriever.Retriever;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import static org.testng.Assert.*;

public class TestImageRetriever extends AbstractVisualSuiteDefinitionTest {

	private final static String SOURCE = "source";
	@Test
	public void testProperties() throws SAXException, IOException {
		String retrieverImpl = AssertingRetriever.class.getName();
		stub.imageRetriever.addAttribute("class", retrieverImpl);
		
		stub.imageRetriever.addElement("xxx").setText("1");
		stub.imageRetriever.addElement("yyy").setText("2");
		
		startWriter();
		parse();
		
		Retriever retriever = handler.getVisualSuite().getGlobalConfiguration().getImageRetriever();
		assertNull(retriever.retrieve(SOURCE, new Properties()));
	}

	public static class AssertingRetriever extends AbstractRetriever {
		@Override
		public BufferedImage retrieve(String source, Properties localProperties) {
			final Properties properties = mergeProperties(localProperties);
			
			assertSame(source, SOURCE);
			assertEquals(properties.get("xxx"), "1");
			assertEquals(properties.get("yyy"), "2");
			
			return null;
		}
	}
}
