package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.suite.Test;

public class TestProcessor extends Processor {

	Test test;
	
	{
		supportProcessor("perception", PerceptionProcessor.class);
		supportProcessor("masks", MasksProcessor.class);
		supportProcessor("pattern", PatternProcessor.class);
	}
	
	@Override
	public void start() {
		test = new Test();
		getContext().setCurrentTest(test);
		getContext().setCurrentConfiguration(test);
	}

	@Override
	public void end() {
		getContext().invokeListeners().testParsed(test);
	}
}
