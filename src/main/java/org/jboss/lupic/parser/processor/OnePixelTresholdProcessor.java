package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class OnePixelTresholdProcessor extends Processor {
	@Override
	public void process(String content) {
		getVisualSuite().getGlobalConfiguration().getPerception()
				.setOnePixelTreshold(Short.valueOf(content));
	}
}
