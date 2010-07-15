package org.jboss.lupic.test.configuration;

import java.util.Iterator;

import org.dom4j.Document;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import static org.jboss.lupic.test.configuration.VisualSuiteDefinitions.*;

@SuppressWarnings("unchecked")
public class ConfigurationStub {

	Document document = DocumentHelper.createDocument();

	Element visualSuite = document.addElement(VISUAL_SUITE).addNamespace("xsi",
			"http://www.w3.org/2001/XMLSchema-instance").addAttribute(
			"xsi:schemaLocation",
			LUPIC_NS.getURI() + " src/main/resources/visual-suite.xsd");

	Element globalConfiguration = visualSuite.addElement(GLOBAL_CONFIGURATION);

	Element imageRetriever = globalConfiguration.addElement(IMAGE_RETRIEVER)
			.addAttribute("class", "org.jboss.lupic.retriever.FileRetriever");
	Element perception = globalConfiguration.addElement(PERCEPTION);
	// Element ignoreBitmapMasks = globalConfiguration.addElement(MASKS)
	// .addAttribute("type", "ignore-bitmap");
	// Element selectiveAlphaMasks = globalConfiguration.addElement(MASKS)
	// .addAttribute("type", "selective-alpha");

	Element defaultTest = visualSuite.addElement(TEST).addAttribute("name",
			"default-test");

	Element defaultPattern = defaultTest.addElement(PATTERN).addAttribute(
			"name", "default-test-pattern");

	Element defaultImage = defaultPattern.addElement(IMAGE);

	{
		Iterator<Element> iterator = visualSuite.elementIterator(TEST);
		if (iterator.next() != defaultTest) {
			throw new IllegalStateException();
		}
		if (iterator.hasNext()) {
			visualSuite.remove(defaultTest);
		}
	}

}
