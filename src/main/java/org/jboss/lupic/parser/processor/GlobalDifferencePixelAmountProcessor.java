package org.jboss.lupic.parser.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.suite.Perception;

public class GlobalDifferencePixelAmountProcessor extends Processor {

	Pattern percentPattern = Pattern.compile("([0-9]{1,2}|100)%");
	Pattern pixelPattern = Pattern.compile("\\d+px");

	@Override
	public void process(String content) {
		Matcher matcher;
		Perception perception = getVisualSuite().getGlobalConfiguration().getPerception();
		for (Pattern pattern : new Pattern[] { percentPattern, pixelPattern }) {
			matcher = pattern.matcher(content);
			if (matcher.lookingAt()) {
				short result = Short.valueOf(matcher.group(1));
				if (pattern == percentPattern) {
					perception.setGlobalDifferencePercentage(result);
				} else {
					perception.setGlobalDifferencePixelAmount(result);
				}
				return;
			}
		}
		throw new IllegalStateException();
	}
}
