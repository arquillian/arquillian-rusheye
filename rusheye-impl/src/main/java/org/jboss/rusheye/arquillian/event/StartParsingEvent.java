/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.rusheye.arquillian.event;

/**
 *
 * @author jhuska
 */
public class StartParsingEvent {

    private String patternAndDescriptorFolder;

    private String samplesFolder;

    public StartParsingEvent(String patternAndDescriptorFolder, String samplesFolder) {
        this.patternAndDescriptorFolder = patternAndDescriptorFolder;
        this.samplesFolder = samplesFolder;
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

}
