package org.jboss.rusheye.arquillian.event;

/**
 *
 * @author jhuska
 */
public class StartParsingEvent {

    private String patternAndDescriptorFolder;

    private String samplesFolder;

    private FailedTestsCollection failedTestsCollection;
    
    private VisuallyUnstableTestsCollection visuallyUnstableCollection;
    
    public StartParsingEvent(String patternAndDescriptorFolder, String samplesFolder, 
            FailedTestsCollection failedTestsCollection, VisuallyUnstableTestsCollection visuallyUnstable) {
        this.patternAndDescriptorFolder = patternAndDescriptorFolder;
        this.samplesFolder = samplesFolder;
        this.failedTestsCollection = failedTestsCollection;
        this.visuallyUnstableCollection = visuallyUnstable;
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

    public VisuallyUnstableTestsCollection getVisuallyUnstableCollection() {
        return visuallyUnstableCollection;
    }

    public void setVisuallyUnstableCollection(VisuallyUnstableTestsCollection visuallyUnstableCollection) {
        this.visuallyUnstableCollection = visuallyUnstableCollection;
    }
}
