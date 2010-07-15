package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class TestProcessor extends Processor {
	{
		supportProcessor("pattern", PatternProcessor.class);
		supportProcessor("masks", MasksProcessor.class);
	}
}
