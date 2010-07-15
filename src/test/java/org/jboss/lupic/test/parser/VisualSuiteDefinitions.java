package org.jboss.lupic.test.parser;

import org.dom4j.Namespace;
import org.dom4j.QName;

public class VisualSuiteDefinitions {
	public static final Namespace LUPIC_NS = new Namespace("",
			"http://www.jboss.org/test/visual-suite");

	public static final QName VISUAL_SUITE = new QName("visual-suite", LUPIC_NS);
	public static final QName GLOBAL_CONFIGURATION = new QName(
			"global-configuration", LUPIC_NS);
	public static final QName IMAGE_RETRIEVER = new QName("image-retriever",
			LUPIC_NS);
	public static final QName PERCEPTION = new QName("perception", LUPIC_NS);
	public static final QName MASKS = new QName("masks", LUPIC_NS);
	public static final QName TEST = new QName("test", LUPIC_NS);
	public static final QName PATTERN = new QName("pattern", LUPIC_NS);
	public static final QName IMAGE = new QName("image", LUPIC_NS);

}
