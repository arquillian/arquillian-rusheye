package org.jboss.lupic.test.parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.output.TeeOutputStream;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jboss.lupic.parser.Handler;
import org.jboss.lupic.parser.Parser;
import org.jboss.lupic.parser.ParserListener;
import org.jboss.lupic.suite.Image;
import org.jboss.lupic.suite.VisualSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLReaderFactory;

import static org.testng.Assert.*;

@SuppressWarnings("unused")
public class AbstractVisualSuiteDefinitionTest {

	String validationFeature = "http://xml.org/sax/features/validation";
	String schemaFeature = "http://apache.org/xml/features/validation/schema";
	String schemaFullChecking = "http://apache.org/xml/features/validation/schema-full-checking";

	VisualSuiteStub stub;
	XMLWriter writer;
	XMLReader reader;
	Handler handler;
	InputSource inputSource;
	String generatedDocument;
	ByteArrayOutputStream documentOutputStream;

	@BeforeMethod
	public void prepareEnvironment() throws IOException, SAXException {
		stub = new VisualSuiteStub();

		PipedInputStream in = new PipedInputStream();
		PipedOutputStream writerOut = new PipedOutputStream(in);
		documentOutputStream = new ByteArrayOutputStream();
		TeeOutputStream out = new TeeOutputStream(writerOut,
				documentOutputStream);

		OutputFormat format = new OutputFormat("\t", true);
		writer = new XMLWriter(out, format);
		inputSource = new InputSource(in);

		reader = XMLReaderFactory.createXMLReader();
		reader.setFeature(validationFeature, true);
		reader.setFeature(schemaFeature, true);
		reader.setFeature(schemaFullChecking, true);
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
				AbstractVisualSuiteDefinitionTest.this.writer
						.write(AbstractVisualSuiteDefinitionTest.this.stub.document);
				AbstractVisualSuiteDefinitionTest.this.writer.close();
				generatedDocument = new String(documentOutputStream
						.toByteArray());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void startWriter() {
		Thread writerThread = new Thread(new WriterRunnable());
		writerThread.start();
	}

	public void parse() throws IOException, SAXException {
		reader.setContentHandler(handler);
		reader.parse(inputSource);
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
		public void imageParsed(VisualSuite visualSuite, Image image) {
			assertEquals(state, 2);
			nextState();
		}

		@Override
		public void suiteCompleted(VisualSuite visualSuite) {
			assertEquals(state, 3);
			nextState();
		}

		private void nextState() {
			state += 1;
		}
	}
}
