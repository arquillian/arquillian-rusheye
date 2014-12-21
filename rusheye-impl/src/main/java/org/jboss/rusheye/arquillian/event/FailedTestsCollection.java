package org.jboss.rusheye.arquillian.event;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jhuska
 */
public class FailedTestsCollection {

    private List<String> tests;

    public FailedTestsCollection() {
        this.tests = new ArrayList<String>();
    }
    
    public void addTest(String testName) {
        tests.add(testName);
    }
    
    public List<String> getTestResults() {
        return tests;
    }

    @Override
    public String toString() {
        return tests.toString();
    }
}
