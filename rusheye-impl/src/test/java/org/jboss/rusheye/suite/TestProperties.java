package org.jboss.rusheye.suite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

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
