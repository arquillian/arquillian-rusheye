/**
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.rusheye.result;

import java.awt.image.BufferedImage;

import org.jboss.rusheye.suite.Pattern;
import org.jboss.rusheye.suite.Properties;
import org.jboss.rusheye.suite.Test;

/**
 * Mechanism for storing difference image from comparison process in case of not same images.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public interface ResultStorage {

    /**
     * Set the properties to be consumed by final implementation.
     * 
     * @param properties
     *            to setup final implementation of storage.
     */
    void setProperties(Properties properties);

    /**
     * Stores the differenceImage to location given by pattern and test.
     * 
     * @param test
     *            tied with pattern and sample which both constructed the differenceImage
     * @param pattern
     *            which constructed differenceImage
     * @param differenceImage
     *            the difference image constructed in process of comparison
     * @return the location where was saved the difference image
     */
    String store(Test test, Pattern pattern, BufferedImage differenceImage);

    /**
     * Invoked on the end of visual suite comparison process to finish the storage mechanism (e.g. close connection,
     * handles).
     */
    void end();
}
