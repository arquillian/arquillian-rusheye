package org.jboss.rusheye.arquillian.event;

/**
 *
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 */
public class StartCrawlinglEvent {

    private String samplesFolder;

    public StartCrawlinglEvent(String samplesFolder) {
        this.samplesFolder = samplesFolder;
    }

    public String getSamplesFolder() {
        return samplesFolder;
    }

    public void setSamplesFolder(String samplesFolder) {
        this.samplesFolder = samplesFolder;
    }
}
