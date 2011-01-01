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
package org.jboss.rusheye.listener;

import org.jboss.rusheye.suite.Configuration;
import org.jboss.rusheye.suite.Pattern;
import org.jboss.rusheye.suite.Properties;
import org.jboss.rusheye.suite.Test;
import org.jboss.rusheye.suite.VisualSuite;

/**
 * Adapter for easy implementation of {@link SuiteListener}.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class SuiteListenerAdapter implements SuiteListener {

    protected Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void onSuiteStarted(VisualSuite visualSuite) {
    }

    public void onSuiteReady(VisualSuite visualSuite) {
    }

    public void onConfigurationReady(VisualSuite visualSuite) {
    }

    public void onTestReady(Test test) {
    }

    public void onPatternReady(Configuration configuration, Pattern pattern) {
    }
}
