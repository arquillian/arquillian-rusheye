package org.jboss.rusheye.arquillian.event;

/**
 *
 * @author jhuska
 */
public class StartParsingEvent {

    private String patternAndDescriptorFolder;

    private String samplesFolder;

    private FailedTestsCollection failedTestsCollection;
    
    public StartParsingEvent(String patternAndDescriptorFolder, String samplesFolder, 
            FailedTestsCollection failedTestsCollection) {
        this.patternAndDescriptorFolder = patternAndDescriptorFolder;
        this.samplesFolder = samplesFolder;
        this.failedTestsCollection = failedTestsCollection;
    }

    public String getSamplesFolder() {
        return samplesFolder;
    }

    public void setSamplesFolder(String samplesFolder) {
        this.samplesFolder = samplesFolder;
    }

    public String getPatternAndDescriptorFolder() {
        return patternAndDescriptorFolder;
    }

    public void setPatternAndDescriptorFolder(String patternAndDescriptorFolder) {
        this.patternAndDescriptorFolder = patternAndDescriptorFolder;
    }

    public FailedTestsCollection getFailedTestsCollection() {
        return failedTestsCollection;
    }

    public void setFailedTestsCollection(FailedTestsCollection failedTestsCollection) {
        this.failedTestsCollection = failedTestsCollection;
    }
}
