package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.suite.GlobalConfiguration;

public class GlobalConfigurationProcessor extends Processor {

	{
		supportProcessor("image-retriever", ImageRetrieverProcessor.class);
		supportProcessor("perception", PerceptionProcessor.class);
		supportProcessor("masks", MasksProcessor.class);
	}

	@Override
	public void start() {
		GlobalConfiguration globalConfiguration = new GlobalConfiguration();
		getVisualSuite().setGlobalConfiguration(globalConfiguration);
	}

	@Override
	public void end() {
		getContext().getListener().configurationParsed(getVisualSuite());
	}
}
