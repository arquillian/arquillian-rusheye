package org.jboss.lupic.parser;

import static org.jboss.lupic.parser.VisualSuiteDefinitions.PATTERN;
import static org.jboss.lupic.parser.VisualSuiteDefinitions.TEST;

import org.dom4j.Element;

public class AbstractTestOfTestElement extends AbstractVisualSuiteDefinitionTest {
    protected static final String TEST1_NAME = "test1-name";
    protected static final String PATTERN1_NAME = "pattern1-name";

    protected static final String TEST2_NAME = "test2-name";
    protected static final String PATTERN2_NAME = "pattern2-name";

    protected static final String PATTERN3_NAME = "pattern3-name";

    protected Element test;
    protected Element pattern;

    protected void addTest(String name) {
        test = stub.visualSuite.addElement(TEST);
        if (name != null) {
            test.addAttribute("name", name);
        }
    }

    protected void addPattern(String name) {
        pattern = test.addElement(PATTERN);
        if (name != null) {
            pattern.addAttribute("name", name);
        }
    }
}
