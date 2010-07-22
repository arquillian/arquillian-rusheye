package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.suite.Perception;

public class OnePixelTresholdProcessor extends Processor {
	@Override
	public void process(String content) {
		Perception perception = getContext().getCurrentConfiguration()
				.getPerception();
		perception.setOnePixelTreshold(Short.valueOf(content));
	}
}
