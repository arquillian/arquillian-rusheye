package org.jboss.lupic.test.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.IOException;

import org.jboss.lupic.parser.ParserListener;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.VisualSuite;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

public class TestSimpleConfiguration extends AbstractVisualSuiteDefinitionTest {
	@Test
	public void testSimpleParse() {
		try {
			startWriter();
			parse();
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

			startWriter();
			parse();

			assertEquals(assertedListener.state, 5);
		} catch (SAXException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
	}

	public class AssertedListener implements ParserListener {

		int state = 0;

		@Override
		public void suiteStarted(VisualSuite visualSuite) {
			assertEquals(state, 0);
			nextState();
		}

		@Override
		public void configurationParsed(VisualSuite visualSuite) {
			assertEquals(state, 1);
			nextState();
		}

		@Override
		public void patternParsed(Configuration configuration, Pattern pattern) {
			assertEquals(state, 2);
			nextState();
		}

		@Override
		public void testParsed(org.jboss.lupic.suite.Test test) {
			assertEquals(state, 3);
			nextState();
		}

		@Override
		public void suiteCompleted(VisualSuite visualSuite) {
			assertEquals(state, 4);
			nextState();
		}

		private void nextState() {
			state += 1;
		}

	}
}
