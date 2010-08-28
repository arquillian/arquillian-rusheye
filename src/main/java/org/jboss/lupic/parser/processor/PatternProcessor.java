package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.retriever.Retriever;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;

public class PatternProcessor extends Processor {
    {
        setPropertiesEnabled(true);
    }

    @Override
    public void end() {
        String name = getAttribute("name");
        String source = getAttribute("source");
        Retriever retriever = getVisualSuite().getGlobalConfiguration().getImageRetriever();

        Pattern pattern = new Pattern(name, source, getProperties(), retriever);

        Configuration globalConfiguration = getVisualSuite().getGlobalConfiguration();
        Configuration testConfiguration = getContext().getCurrentConfiguration();

        Configuration patternConfiguration = new Configuration();
        patternConfiguration.setValuesFromParent(globalConfiguration);
        patternConfiguration.setValuesFromParent(testConfiguration);
        patternConfiguration.setDefaultValuesForUnset();

        getContext().invokeListeners().patternParsed(patternConfiguration, pattern);
    }
}
