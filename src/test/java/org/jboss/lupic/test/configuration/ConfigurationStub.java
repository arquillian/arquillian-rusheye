package org.jboss.lupic.test.configuration;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ConfigurationStub {
	Document document = DocumentHelper.createDocument();

	Element root = document.addElement("visual-suite");

	Element globalConfiguration = root.addElement("global-configuration");

	Element imageRetriever = globalConfiguration.addElement("image-retriever")
			.addAttribute("class", "org.jboss.lupic.retriever.FileRetriever");
	Element perception = globalConfiguration.addElement("perception");
	Element ignoreBitmapMasks = globalConfiguration.addElement("masks")
			.addAttribute("type", "ignore-bitmap");
	Element selectiveAlphaMasks = globalConfiguration.addElement("masks")
			.addAttribute("type", "selective-alpha");
}
