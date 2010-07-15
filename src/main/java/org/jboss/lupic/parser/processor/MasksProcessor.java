package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;

public class MasksProcessor extends Processor {
	
	{
		supportProcessor("mask", MaskProcessor.class);
	}
	
	@Override
	public void start() {
		String type = getAttribute("type");
		
		if ("ignore-bitmap".equals(type)) {
			getContext().setCurrentMasks(getVisualSuite().getGlobalConfiguration().getIgnoreBitmapMasks());
		} else if ("selective-alpha".equals(type)) {
			getContext().setCurrentMasks(getVisualSuite().getGlobalConfiguration().getSelectiveAlphaMasks());
		} else {
			throw new IllegalStateException("The Mask must define it's type (ignore-bitmap|selective-alpha)");
		}
	}
}
