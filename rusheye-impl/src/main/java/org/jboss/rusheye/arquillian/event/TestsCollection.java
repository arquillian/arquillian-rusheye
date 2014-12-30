package org.jboss.rusheye.arquillian.event;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jhuska
 */
public abstract class TestsCollection {
    
    private List<String> tests = new ArrayList<String>();

    public TestsCollection(List<String> tests) {
        this.tests = tests;
    }

    public TestsCollection() {
    }

    public List<String> getTests() {
        return tests;
    }

    public void setTests(List<String> tests) {
        this.tests = tests;
    }
    
    public void addTest(String testName) {
        tests.add(testName);
    }

    @Override
    public String toString() {
        return "TestsCollection{" + "tests=" + tests + '}';
    }
}
