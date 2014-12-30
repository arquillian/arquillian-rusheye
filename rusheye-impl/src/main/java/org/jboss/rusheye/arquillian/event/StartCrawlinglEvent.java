package org.jboss.rusheye.arquillian.event;

/**
 *
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 */
public class StartCrawlinglEvent {

    private String samplesFolder;
    
    private FailedTestsCollection failedTestsCollection;
    
    private VisuallyUnstableTestsCollection visuallyUnstableCollection;

    public StartCrawlinglEvent(String samplesFolder, FailedTestsCollection failedTestsCollection, 
            VisuallyUnstableTestsCollection visuallyUnstableCollection) {
        this.samplesFolder = samplesFolder;
        this.failedTestsCollection = failedTestsCollection;
        this.visuallyUnstableCollection = visuallyUnstableCollection;
    }

    public FailedTestsCollection getFailedTestsCollection() {
        return failedTestsCollection;
    }

    public void setFailedTestsCollection(FailedTestsCollection failedTestsCollection) {
        this.failedTestsCollection = failedTestsCollection;
    }

    public String getSamplesFolder() {
        return samplesFolder;
    }

    public void setSamplesFolder(String samplesFolder) {
        this.samplesFolder = samplesFolder;
    }

    public VisuallyUnstableTestsCollection getVisuallyUnstableCollection() {
        return visuallyUnstableCollection;
    }

    public void setVisuallyUnstableCollection(VisuallyUnstableTestsCollection visuallyUnstableCollection) {
        this.visuallyUnstableCollection = visuallyUnstableCollection;
    }
}
