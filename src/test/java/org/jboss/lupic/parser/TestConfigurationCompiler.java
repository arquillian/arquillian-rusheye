package org.jboss.lupic.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.jboss.lupic.suite.GlobalConfiguration;
import org.jboss.lupic.suite.Perception;
import org.jboss.lupic.suite.Test;
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
        assertEquals(wrapped.getPerception().getGlobalDifferenceTreshold(), new Integer(10));
        assertEquals(wrapped.getPerception().getOnePixelTreshold(), new Integer(50));

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
        assertEquals(wrapped.getPerception().getGlobalDifferenceTreshold(), new Integer(10));
        assertEquals(wrapped.getPerception().getOnePixelTreshold(), new Integer(50));

        test.getPerception().setGlobalDifferencePercentage((short) 54);
        assertEquals(wrapped.getPerception().getGlobalDifferencePercentage(), new Short((short) 54));
        assertEquals(wrapped.getPerception().getGlobalDifferencePixelAmount(), null);
    }
}
