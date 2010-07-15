package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class ImageProcessor extends Processor {
	{
		setPropertiesEnabled(true);
	}
	
	@Override
	public void end() {
		// FIXME should provide the real image representation
		getContext().getListener().imageParsed(getVisualSuite(), null);
	}
}
