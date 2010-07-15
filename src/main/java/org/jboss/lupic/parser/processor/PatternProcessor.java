package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class PatternProcessor extends Processor {
	{
		supportProcessor("image", ImageProcessor.class);
		supportProcessor("perception", PerceptionProcessor.class);
	}
}
