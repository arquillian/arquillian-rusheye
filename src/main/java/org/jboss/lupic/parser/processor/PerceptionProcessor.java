package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class PerceptionProcessor extends Processor {
	{
		supportProcessor("one-pixel-treshold", OnePixelTresholdProcessor.class);
		supportProcessor("global-difference-treshold", GlobalDifferenceTresholdProcessor.class);
		supportProcessor("global-difference-pixel-amount", GlobalDifferencePixelAmountProcessor.class);
	}
}
