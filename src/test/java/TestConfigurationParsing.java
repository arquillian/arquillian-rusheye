import java.io.IOException;
import java.io.InputStream;

import org.jboss.lupic.parser.Handler;
import org.jboss.lupic.parser.Parser;
import org.jboss.lupic.parser.ParserListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import static org.testng.Assert.*;

public class TestConfigurationParsing {

	XMLReader parser;
	Handler handler;
	InputSource inputSource;

	@BeforeMethod
	public void prepareEnvironment() throws SAXException {
		final String resourceName = "parser-input/configuration.xml";

		InputStream inputStream = Parser.class.getClassLoader()
				.getResourceAsStream(resourceName);
		inputSource = new InputSource(inputStream);

		parser = XMLReaderFactory.createXMLReader();

		handler = new Handler();
	}

	@Test
	public void testSimpleParse() {
		try {
			parser.setContentHandler(handler);
			parser.parse(inputSource);
		} catch (SAXException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void testListening() {
		try {
			AssertedListener assertedListener = new AssertedListener();
			handler.registerListener(assertedListener);
			parser.setContentHandler(handler);
			parser.parse(inputSource);
			assertEquals(assertedListener.state, 3);
		} catch (SAXException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
	}

	public class AssertedListener implements ParserListener {

		int state = 0;

		@Override
		public void suiteStarted() {
			assertEquals(state, 0);
			nextState();
		}

		@Override
		public void configurationParsed() {
			assertEquals(state, 1);
			nextState();
		}

		@Override
		public void suiteCompleted() {
			assertEquals(state, 2);
			nextState();
		}

		@Override
		public void imageParsed() {
			fail();
		}

		private void nextState() {
			state += 1;
		}
	}
}
