package org.jboss.lupic.test.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.IOException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

public class TestSimpleConfiguration extends AbstractVisualSuiteDefinitionTest {
	@Test
	public void testSimpleParse() {
		try {
			reader.setContentHandler(handler);
			reader.parse(inputSource);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGoThroughAllPhases() {
		try {
			AssertedListener assertedListener = new AssertedListener();
			handler.registerListener(assertedListener);

			reader.setContentHandler(handler);
			reader.parse(inputSource);

			assertEquals(assertedListener.state, 4);
		} catch (SAXException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
	}
}
