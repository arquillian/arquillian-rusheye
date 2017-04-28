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
package org.arquillian.rusheye.listener;

import org.arquillian.rusheye.suite.Configuration;
import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Properties;
import org.arquillian.rusheye.suite.Test;
import org.arquillian.rusheye.suite.VisualSuite;

/**
 * <p>
 * The listener as starting point of processing suite.
 * </p>
 *
 * <p>
 * As the configuration of suite is loaded to the runtime, the appropriate events should be fired to process suite.
 * </p>
 *
 * <p>
 * The readiness in this meaning signs the existence of all details of particular object.
 * </p>
 *
 * <p>
 * The implementation needs to satisfy the ordering of fired action as follows:
 * </p>
 *
 * <ul>
 * <li>properties are set</li>
 * <li>suite is started</li>
 * <li>global configuration came ready</li>
 * <li>pattern came ready</li>
 * <li>test came ready</li>
 * <li>suite came ready</li>
 * </ul>
 *
 * <p>
 * It specifically means:
 * </p>
 *
 * <ul>
 * <li>particular patterns need to be all ready before their test gets ready</li>
 * <li>all tests are ready before suite gets ready</li>
 * </ul>
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public interface SuiteListener {
    /**
     * Sets the properties of listener, this properties needs to be propagated to all subsequent listeners.
     *
     * @param properties
     *     which will be set to this listener and all subsequent listeners
     */
    void setProperties(Properties properties);

    /**
     * Fired when processing of the suite starts.
     *
     * @param visualSuite
     *     the actual form of visual suite, in this step still without all of the necessary parts
     */
    void onSuiteStarted(VisualSuite visualSuite);

    /**
     * Fired when global configuration came ready.
     *
     * @param visualSuite
     *     visual suite with global configuration ready.
     */
    void onConfigurationReady(VisualSuite visualSuite);

    /**
     * Fired when pattern is ready to be processed.
     *
     * @param configuration
     *     the configuration completely ready for process this pattern
     * @param pattern
     *     ready to be processed
     */
    void onPatternReady(Configuration configuration, Pattern pattern);

    /**
     * Fired when particular test and all of it's patterns are ready.
     *
     * @param test
     *     which came ready including all of it's patterns
     */
    void onTestReady(Test test);

    /**
     * Fired when all of it's test are ready to be processed.
     *
     * @param visualSuite
     *     the completed details of visual suite configuration including all it's tests
     */
    void onSuiteReady(VisualSuite visualSuite);
}
