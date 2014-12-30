package org.jboss.rusheye.arquillian.event;

import java.util.List;

/**
 *
 * @author jhuska
 */
public class VisuallyUnstableTestsCollection extends TestsCollection {

    public VisuallyUnstableTestsCollection(List<String> tests) {
        super(tests);
    }

    public VisuallyUnstableTestsCollection() {
    }
}
