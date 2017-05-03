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
package org.arquillian.rusheye.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.arquillian.rusheye.parser.DefaultConfiguration.*;

import org.arquillian.rusheye.suite.GlobalConfiguration;
import org.arquillian.rusheye.suite.Perception;
import org.arquillian.rusheye.suite.Test;
import org.testng.annotations.BeforeMethod;

public class TestConfigurationCompiler {

    Test test;

    @BeforeMethod
    public void initializeTest() {
        test = new Test();
        test.setName("testname");
        test.setPerception(new Perception());
    }

    @org.testng.annotations.Test
    public void testHashcode() {
        Test wrapped = ConfigurationCompiler.wrap(test);

        assertEquals(wrapped.hashCode(), test.hashCode());
    }

    @org.testng.annotations.Test
    public void testEquals() {
        Test wrapped = ConfigurationCompiler.wrap(test);

        assertEquals(wrapped, test);
    }

    @org.testng.annotations.Test
    public void testWrapping() {
        test.getPerception().setGlobalDifferenceAmount("73px");

        Test wrapped = ConfigurationCompiler.wrap(test);
        assertEquals(wrapped.getName(), "testname");
        assertEquals(wrapped.getPerception().getGlobalDifferencePixelAmount(), new Long(73));
        assertNull(wrapped.getPerception().getGlobalDifferencePercentage());
        assertEquals(wrapped.getPerception().getGlobalDifferenceTreshold(), new Float(DEFAULT_GLOBAL_DIFFERENCE_TRESHOLD));
        assertEquals(wrapped.getPerception().getOnePixelTreshold(), new Float(DEFAULT_ONE_PIXEL_TRESHOLD));

        test.getPerception().setGlobalDifferencePercentage((short) 54);
        assertEquals(wrapped.getPerception().getGlobalDifferencePercentage(), new Short((short) 54));
        assertEquals(wrapped.getPerception().getGlobalDifferencePixelAmount(), null);
    }

    @org.testng.annotations.Test
    public void testWrappingWithGlobalConfiguration() {
        test.getPerception().setGlobalDifferenceAmount("73px");

        GlobalConfiguration globalConfiguration = new GlobalConfiguration();

        Test wrapped = ConfigurationCompiler.wrap(test, globalConfiguration);
        assertEquals(wrapped.getName(), "testname");
        assertEquals(wrapped.getPerception().getGlobalDifferencePixelAmount(), new Long(73));
        assertNull(wrapped.getPerception().getGlobalDifferencePercentage());
        assertEquals(wrapped.getPerception().getGlobalDifferenceTreshold(), new Float(DEFAULT_GLOBAL_DIFFERENCE_TRESHOLD));
        assertEquals(wrapped.getPerception().getOnePixelTreshold(), new Float(DEFAULT_ONE_PIXEL_TRESHOLD));

        test.getPerception().setGlobalDifferencePercentage((short) 54);
        assertEquals(wrapped.getPerception().getGlobalDifferencePercentage(), new Short((short) 54));
        assertEquals(wrapped.getPerception().getGlobalDifferencePixelAmount(), null);
    }
}
