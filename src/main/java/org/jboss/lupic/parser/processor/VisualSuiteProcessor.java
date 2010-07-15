package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class VisualSuiteProcessor extends Processor {
	{
		supportProcessor("global-configuration", GlobalConfigurationProcessor.class);
		supportProcessor("test", TestProcessor.class);
	}
}
