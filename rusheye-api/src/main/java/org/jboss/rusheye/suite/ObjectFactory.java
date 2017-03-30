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

import javax.xml.bind.annotation.XmlRegistry;

/**
 * The implementation of ObjectFactory to satisfy JAXB package convention made possible to construct new object from
 * this package.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Perception createPerception() {
        return new Perception();
    }

    public VisualSuite createVisualSuite() {
        return new VisualSuite();
    }

    public SuiteListenerImpl createListener() {
        return new SuiteListenerImpl();
    }

    public SampleRetrieverImpl createSampleRetriever() {
        return new SampleRetrieverImpl();
    }

    public Sample createSample() {
        return new Sample();
    }

    public Mask createMask() {
        return new Mask();
    }

    public Pattern createPattern() {
        return new Pattern();
    }

    public Test createTest() {
        return new Test();
    }

    public PatternRetrieverImpl createPatternRetriever() {
        return new PatternRetrieverImpl();
    }

    public MaskRetrieverImpl createMaskRetriever() {
        return new MaskRetrieverImpl();
    }

    public GlobalConfiguration createGlobalConfiguration() {
        return new GlobalConfiguration();
    }

    public Properties createProperties() {
        return new Properties();
    }
}
