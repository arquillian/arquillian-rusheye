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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class TestProperties {

    @Test
    public void testSettersGetters() {
        Properties properties = new Properties();
        properties.setProperty("xxx", "1");
        properties.setProperty("yyy", "2");
        assertEquals(properties.getProperty("xxx"), "1");
        assertEquals(properties.getProperty("yyy"), "2");
    }

    @Test
    public void testRedefiniton() {
        Properties properties = new Properties();
        properties.setProperty("xxx", "1");
        properties.setProperty("yyy", "2");
        properties.setProperty("xxx", "3");
        properties.setProperty("yyy", "4");
        assertEquals(properties.getProperty("xxx"), "3");
        assertEquals(properties.getProperty("yyy"), "4");
    }

    @Test
    public void testInclusion() {
        Properties properties1 = new Properties();
        properties1.setProperty("xxx", "1");

        Properties properties2 = new Properties();
        properties2.setProperty("yyy", "2");

        properties1.include(properties2);

        assertEquals(properties1.getProperty("yyy"), "2");
        assertEquals(properties1.getProperty("xxx"), "1");

        assertNull(properties2.getProperty("xxx"));
        assertEquals(properties2.getProperty("yyy"), "2");
    }

    @Test
    public void testInclusionRedefinition() {
        Properties properties1 = new Properties();
        properties1.setProperty("xxx", "1");

        Properties properties2 = new Properties();
        properties2.setProperty("yyy", "2");

        properties1.include(properties2);

        properties1.setProperty("xxx", "3");
        assertEquals(properties1.getProperty("xxx"), "3");
        assertEquals(properties1.getProperty("yyy"), "2");

        properties2.setProperty("yyy", "4");
        assertEquals(properties1.getProperty("xxx"), "3");
        assertEquals(properties2.getProperty("yyy"), "4");
    }
}
