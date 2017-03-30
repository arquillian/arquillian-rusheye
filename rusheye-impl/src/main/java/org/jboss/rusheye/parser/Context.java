/**
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.rusheye.parser;

import java.util.HashSet;
import java.util.Set;
import org.jboss.rusheye.listener.SuiteListener;
import org.jboss.rusheye.suite.Configuration;
import org.jboss.rusheye.suite.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class Context {
    private Test currentTest;
    private Configuration currentConfiguration;
    private Set<String> testNames = new HashSet<String>();
    private Set<String> maskIds = new HashSet<String>();
    private Set<String> patternNames = new HashSet<String>();

    public Configuration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(Configuration currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }

    public Test getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(Test currentTest) {
        this.currentTest = currentTest;
    }

    public Set<String> getTestNames() {
        return testNames;
    }

    public Set<String> getPatternNames() {
        return patternNames;
    }

    public Set<String> getMaskIds() {
        return maskIds;
    }

    public abstract SuiteListener invokeListeners();
}
