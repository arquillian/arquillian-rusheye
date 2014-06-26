package org.jboss.rusheye.arquillian.configuration;

import java.io.File;

/**
 *
 * @author jhuska
 */
public class RusheyeConfiguration extends Configuration<RusheyeConfiguration> {
    
    private String output = "/tmp/bar";
    
    private String samplesDirectory = "/tmp/bar";
    
    private String patternBase = "/tmp/bar";
    
    private String fileStorageDirectory = "/tmp/bar";
    
    private String maskBase = ".";
    
    private String onePixelTreshold;
    
    private String globalDifferenceTreshold;
    
    private String globalDifferenceAmount;
    
    public String getGlobalDifferenceAmount() {
        return globalDifferenceAmount;
    }
    
    public String getGlobalDifferenceTreshold() {
        return globalDifferenceTreshold;
    }
    
    public String getOnePixelTreshold() {
        return onePixelTreshold;
    }
    
    public File getMaskBase() {
        return new File(getProperty("maskBase", maskBase));
    }
    
    public File getOutput() {
        return new File(getProperty("output", output));
    }

    public File getSamplesDirectory() {
        return new File(getProperty("samplesDirectory", samplesDirectory));
    }

    public File getPatternBase() {
        return new File(getProperty("patternBase", patternBase));
    }

    public File getFileStorageDirectory() {
        return new File(getProperty("fileStorageDirectory", fileStorageDirectory));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-40s %s\n", "resultOutputFile", getOutput()));
        sb.append(String.format("%-40s %s\n", "samplesDirectory", getSamplesDirectory()));
        sb.append(String.format("%-40s %s\n", "patternsDirectory", getPatternBase()));
        sb.append(String.format("%-40s %s\n", "fileStorageDirectory", getFileStorageDirectory()));
        return sb.toString();
    }

    @Override
    public void validate() throws RusheyeConfigurationException {
        
    }
}
