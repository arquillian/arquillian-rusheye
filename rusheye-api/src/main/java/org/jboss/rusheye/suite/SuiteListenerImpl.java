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
package org.jboss.rusheye.suite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.rusheye.internal.Instantiator;
import org.jboss.rusheye.listener.SuiteListener;

/**
 * The encapsulation of the type of listener.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Listener")
public class SuiteListenerImpl extends TypeProperties implements SuiteListener {

    @XmlTransient
    SuiteListener suiteListener;

    private void initializeListener() {
        if (suiteListener == null) {
            if (getType() == null) {
                throw new IllegalStateException("type of the mask retriver can't be null");
            }
            suiteListener = new Instantiator<SuiteListener>().getInstance(getType());
            suiteListener.setProperties(this);
        }
    }

    public void setProperties(Properties properties) {
        initializeListener();
        suiteListener.setProperties(properties);
    }

    public void onSuiteStarted(VisualSuite visualSuite) {
        initializeListener();
        suiteListener.onSuiteStarted(visualSuite);
    }

    public void onConfigurationReady(VisualSuite visualSuite) {
        initializeListener();
        suiteListener.onConfigurationReady(visualSuite);
    }

    public void onPatternReady(Configuration configuration, Pattern pattern) {
        initializeListener();
        suiteListener.onPatternReady(configuration, pattern);
    }

    public void onTestReady(Test test) {
        initializeListener();
        suiteListener.onTestReady(test);

    }

    public void onSuiteReady(VisualSuite visualSuite) {
        initializeListener();
        suiteListener.onSuiteReady(visualSuite);
    }
}
