package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class GlobalDifferenceTresholdProcessor extends Processor {
	@Override
	public void process(String content) {
		getVisualSuite().getGlobalConfiguration().getPerception()
				.setGlobalDifferenceTreshold(Short.valueOf(content));
	}
}
