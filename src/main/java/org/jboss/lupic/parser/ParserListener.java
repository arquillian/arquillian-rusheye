package org.jboss.lupic.parser;

import org.jboss.lupic.suite.Image;
import org.jboss.lupic.suite.VisualSuite;

public interface ParserListener {
	void configurationParsed(VisualSuite visualSuite);

	void imageParsed(VisualSuite visualSuite, Image image);

	void suiteStarted(VisualSuite visualSuite);

	void suiteCompleted(VisualSuite visualSuite);
}
