package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class TestProcessor extends Processor {
	{
		supportProcessor("pattern", PatternProcessor.class);
		supportProcessor("masks", MasksProcessor.class);
		supportProcessor("perception", PerceptionProcessor.class);
	}
	
	@Override
	public void end() {
		// FIXME should provide the real test representation
		getContext().getListener().testParsed(getVisualSuite(), null);
	}
}
