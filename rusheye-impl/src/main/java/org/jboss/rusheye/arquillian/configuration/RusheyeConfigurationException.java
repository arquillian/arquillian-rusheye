/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jboss.rusheye.arquillian.configuration;

/**
 *
 * @author jhuska
 */
public class RusheyeConfigurationException extends RuntimeException {
    
    private static final long serialVersionUID = 6129116367777096L;
    
    public RusheyeConfigurationException() {
        super();
    }

    public RusheyeConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RusheyeConfigurationException(String message) {
        super(message);
    }

    public RusheyeConfigurationException(Throwable cause) {
        super(cause);
    }
    
}
