package org.jboss.lupic.parser;

import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Test;
import org.jboss.lupic.suite.VisualSuite;

public interface ParserListener {
	void configurationParsed(VisualSuite visualSuite);

	void testParsed(VisualSuite visualSuite, Test test);
	
	void patternParsed(VisualSuite visualSuite, Pattern pattern);

	void suiteStarted(VisualSuite visualSuite);

	void suiteCompleted(VisualSuite visualSuite);
}
