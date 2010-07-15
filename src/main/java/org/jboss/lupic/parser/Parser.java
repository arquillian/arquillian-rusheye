package org.jboss.lupic.parser;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Parser {
	public static final String URI = "http://www.jboss.org/test/visual-suite";

	public static void parse(String resourceName) throws SAXException,
			IOException {
		InputStream inputStream = Parser.class.getClassLoader()
				.getResourceAsStream(resourceName);
		InputSource inputSource = new InputSource(inputStream);
		parse(inputSource);

	}

	public static void parse(InputSource input) throws SAXException,
			IOException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		parser.setContentHandler(new Handler());
		parser.parse(input);
	}
}
