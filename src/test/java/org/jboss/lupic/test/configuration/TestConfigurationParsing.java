package org.jboss.lupic.test.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.output.TeeOutputStream;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jboss.lupic.parser.Handler;
import org.jboss.lupic.parser.Parser;
import org.jboss.lupic.parser.ParserListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import static org.testng.Assert.*;

@SuppressWarnings("unused")
public class TestConfigurationParsing {

	String validationFeature = "http://xml.org/sax/features/validation";
	String schemaFeature = "http://apache.org/xml/features/validation/schema";

	Document document;
	XMLWriter writer;
	XMLReader reader;
	Handler handler;
	InputSource inputSource;

	@BeforeMethod
	public void prepareEnvironment() throws SAXException, IOException {
		ConfigurationStub configurationSample = new ConfigurationStub();
		document = configurationSample.document;

		PipedInputStream in = new PipedInputStream();
		PipedOutputStream writerOut = new PipedOutputStream(in);
		TeeOutputStream out = new TeeOutputStream(writerOut, System.out);
		OutputFormat format = new OutputFormat("\t", true);
		writer = new XMLWriter(out, format);
		inputSource = new InputSource(in);
		reader = XMLReaderFactory.createXMLReader();
		reader.setFeature(validationFeature, true);
		reader.setFeature(schemaFeature, true);
		reader.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException e) throws SAXException {
				throw e;
			}

			@Override
			public void fatalError(SAXParseException e) throws SAXException {
				throw e;
			}

			@Override
			public void error(SAXParseException e) throws SAXException {
				throw e;
			}
		});

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
		public void imageParsed() {
			assertEquals(state, 2);
			nextState();
		}

		@Override
		public void suiteCompleted() {
			assertEquals(state, 3);
			nextState();
		}

		private void nextState() {
			state += 1;
		}
	}
}
