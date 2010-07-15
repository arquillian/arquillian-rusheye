package org.jboss.lupic.test.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.io.XMLWriter;
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

@SuppressWarnings("unused")
public class TestConfigurationParsing {

	Document document;
	XMLWriter writer;
	XMLReader parser;
	Handler handler;
	InputSource inputSource;

	@BeforeMethod
	public void prepareEnvironment() throws SAXException, IOException {
		ConfigurationStub configurationSample = new ConfigurationStub();
		document = configurationSample.document;

		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream(in);
		writer = new XMLWriter(out);
		inputSource = new InputSource(in);
		parser = XMLReaderFactory.createXMLReader();
		handler = new Handler();
	}

	private class WriterRunnable implements Runnable {
		@Override
		public void run() {
			try {
				TestConfigurationParsing.this.writer
						.write(TestConfigurationParsing.this.document);
				TestConfigurationParsing.this.writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@BeforeMethod
	private void startWriter() {
		Thread writerThread = new Thread(new WriterRunnable());
		writerThread.start();
	}

	@Test
	public void testSimpleParse() {
		try {
			parser.setContentHandler(handler);
			parser.parse(inputSource);
		} catch (SAXException e) {
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGoThroughAllPhases() {
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
