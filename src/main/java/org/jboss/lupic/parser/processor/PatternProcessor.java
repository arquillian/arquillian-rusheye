package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class PatternProcessor extends Processor {
	{
		setPropertiesEnabled(true);
	}

	@Override
	public void end() {
		// FIXME should provide the real pattern representation
		getContext().getListener().patternParsed(getVisualSuite(), null);
	}
}
