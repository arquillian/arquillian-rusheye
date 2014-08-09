/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jboss.rusheye.arquillian.observer;

/**
 *
 * @author jhuska
 */
public final class PrintErrorUtils {
    
    private PrintErrorUtils() {
    }
    
    public static void printErrorMessage(Exception e) {
        System.err.println(e.getMessage());
    }
}
