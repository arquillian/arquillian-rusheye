package org.jboss.rusheye.arquillian.event;

import java.util.List;

/**
 *
 * @author jhuska
 */
public class FailedTestsCollection extends TestsCollection {

    public FailedTestsCollection(List<String> tests) {
        super(tests);
    }

    public FailedTestsCollection() {
    }
}
