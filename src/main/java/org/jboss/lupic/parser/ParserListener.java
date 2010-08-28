package org.jboss.lupic.parser;

import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Test;
import org.jboss.lupic.suite.VisualSuite;

public interface ParserListener {
    void suiteStarted(VisualSuite visualSuite);

    void suiteCompleted(VisualSuite visualSuite);

    void configurationParsed(VisualSuite visualSuite);

    void testParsed(Test test);

    void patternParsed(Configuration configuration, Pattern pattern);
}
