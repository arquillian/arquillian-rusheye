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
package org.arquillian.rusheye.result;

import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Properties;
import org.arquillian.rusheye.suite.Test;

/**
 * The derivation of {@link ResultCollector} which is dedicated to collect statistics of completion.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public interface ResultStatistics {

    /**
     * Set the properties to be consumed by final implementation.
     *
     * @param properties
     *     to setup final implementation of statistics.
     */
    void setProperties(Properties properties);

    /**
     * Fired when pattern completed.
     *
     * @param pattern
     *     the detail of pattern comparison
     */
    void onPatternCompleted(Pattern pattern);

    /**
     * Fired when test completed including all it's pattern completed.
     *
     * @param test
     *     the test which completed
     */
    void onTestCompleted(Test test);

    /**
     * Fired when whole suite completed and there will be no more result details available.
     */
    void onSuiteCompleted();
}
