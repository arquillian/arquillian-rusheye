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
package org.arquillian.rusheye.suite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.arquillian.rusheye.internal.Instantiator;
import org.arquillian.rusheye.listener.SuiteListener;

/**
 * <p>
 * Proxy for actual implementation of {@link SuiteListener}.
 * </p>
 *
 * <p>
 * Actual implementation will be constructed from {@link #getType()} value.
 * </p>
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Listener")
public class SuiteListenerImpl extends TypeProperties implements SuiteListener {

    /**
     * Reference to actual implementation of suite listener.
     */
    @XmlTransient
    private SuiteListener suiteListener;

    /**
     * Initializes actual implementation of suite listener.
     */
    private void initializeListener() {
        if (suiteListener == null) {
            if (getType() == null) {
                throw new IllegalStateException("type of the mask retriver can't be null");
            }
            suiteListener = new Instantiator<SuiteListener>().getInstance(getType());
            suiteListener.setProperties(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.arquillian.rusheye.listener.SuiteListener#setProperties(org.arquillian.rusheye.suite.Properties)
     */
    public void setProperties(Properties properties) {
        initializeListener();
        suiteListener.setProperties(properties);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.arquillian.rusheye.listener.SuiteListener#onSuiteStarted(org.arquillian.rusheye.suite.VisualSuite)
     */
    public void onSuiteStarted(VisualSuite visualSuite) {
        initializeListener();
        suiteListener.onSuiteStarted(visualSuite);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.arquillian.rusheye.listener.SuiteListener#onConfigurationReady(org.arquillian.rusheye.suite.VisualSuite)
     */
    public void onConfigurationReady(VisualSuite visualSuite) {
        initializeListener();
        suiteListener.onConfigurationReady(visualSuite);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.arquillian.rusheye.listener.SuiteListener#onPatternReady(org.arquillian.rusheye.suite.Configuration,
     * org.arquillian.rusheye.suite.Pattern)
     */
    public void onPatternReady(Configuration configuration, Pattern pattern) {
        initializeListener();
        suiteListener.onPatternReady(configuration, pattern);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.arquillian.rusheye.listener.SuiteListener#onTestReady(org.arquillian.rusheye.suite.Test)
     */
    public void onTestReady(Test test) {
        initializeListener();
        suiteListener.onTestReady(test);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.arquillian.rusheye.listener.SuiteListener#onSuiteReady(org.arquillian.rusheye.suite.VisualSuite)
     */
    public void onSuiteReady(VisualSuite visualSuite) {
        initializeListener();
        suiteListener.onSuiteReady(visualSuite);
    }
}
