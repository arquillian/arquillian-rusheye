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
package org.jboss.rusheye.suite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The representation of visual test suite containing global configuration and list of tests.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "VisualSuite", propOrder = {"globalConfiguration", "tests"})
@XmlRootElement(name = "visual-suite")
public class VisualSuite {

    /** The global configuration. */
    protected GlobalConfiguration globalConfiguration;

    /** The list of tests */
    protected List<Test> tests;

    /**
     * Gets the global configuration.
     *
     * @return the global configuration
     */
    @XmlElement(name = "global-configuration", required = true)
    public GlobalConfiguration getGlobalConfiguration() {
        return globalConfiguration;
    }

    /**
     * Sets the global configuration.
     *
     * @param value
     *     the new global configuration
     */
    public void setGlobalConfiguration(GlobalConfiguration value) {
        this.globalConfiguration = value;
    }

    /**
     * Gets the test.
     *
     * @return the test
     */
    @XmlElement(name = "test", required = true)
    public List<Test> getTests() {
        if (tests == null) {
            tests = new ArrayList<Test>();
        }
        return this.tests;
    }
}
