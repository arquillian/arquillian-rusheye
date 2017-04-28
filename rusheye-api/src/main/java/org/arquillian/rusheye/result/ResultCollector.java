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

import org.arquillian.rusheye.listener.SuiteListener;
import org.arquillian.rusheye.suite.ComparisonResult;
import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Test;
import org.arquillian.rusheye.suite.VisualSuite;

/**
 * Interface for event listeners collecting results of the comparison process.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public interface ResultCollector extends SuiteListener {

    /**
     * Fired when sample retrieval was started.
     *
     * @param test
     *     the test for which was sample retrieval started
     */
    void onSampleStarted(Test test);

    /**
     * Fired when sample was retrieved.
     *
     * @param test
     *     the test for which was sample retrieved
     */
    void onSampleLoaded(Test test);

    /**
     * Fired when pattern retrieval was started.
     *
     * @param pattern
     *     the pattern for which was sample retrieval started
     */
    void onPatternStarted(Pattern pattern);

    /**
     * Fired when pattern was retrieved.
     *
     * @param test
     *     the test tied with pattern for which was pattern retrieved.
     * @param pattern
     *     the pattern which was retrieved.
     */
    void onPatternLoaded(Test test, Pattern pattern);

    /**
     * Fired when comparison process of pattern was completed.
     *
     * @param test
     *     the test tied to pattern which was completed
     * @param pattern
     *     the pattern which was completed
     * @param comparisonResult
     *     the comparison result
     */
    void onPatternCompleted(Test test, Pattern pattern, ComparisonResult comparisonResult);

    /**
     * Fired when test was started.
     *
     * @param test
     *     test which was started
     */
    void onTestStarted(Test test);

    /**
     * Fired when test was completed
     *
     * @param test
     *     the test which was completed
     */
    void onTestCompleted(Test test);

    /**
     * Fired when whole suite was completed
     *
     * @param visualSuite
     *     the visual suite which was completed
     */
    void onSuiteCompleted(VisualSuite visualSuite);
}
