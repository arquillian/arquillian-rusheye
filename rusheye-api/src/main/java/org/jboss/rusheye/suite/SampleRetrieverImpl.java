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

import java.awt.image.BufferedImage;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.jboss.rusheye.exception.RetrieverException;
import org.jboss.rusheye.internal.Instantiator;
import org.jboss.rusheye.retriever.SampleRetriever;

/**
 * <p>
 * Proxy for actual implementation of {@link SampleRetriever}.
 * </p>
 *
 * <p>
 * The actual implementation is constructed from the {@link #getType()} value.
 * </p>
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "SampleRetriever")
public class SampleRetrieverImpl extends TypeProperties implements SampleRetriever {

    /**
     * The reference to actual implementation of sample retriever
     */
    @XmlTransient
    private SampleRetriever sampleRetriever;

    /**
     * Initializes the actual implementation of sample retriever
     */
    private void initializeRetriever() {
        if (sampleRetriever == null) {
            if (getType() == null) {
                throw new IllegalStateException("type of the sample retriver can't be null");
            }
            sampleRetriever = new Instantiator<SampleRetriever>().getInstance(getType());
            sampleRetriever.setGlobalProperties(this);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.jboss.rusheye.retriever.Retriever#retrieve(java.lang.String, org.jboss.rusheye.suite.Properties)
     */
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        initializeRetriever();
        return sampleRetriever.retrieve(source, localProperties);
    }

    /*
     * (non-Javadoc)
     * @see org.jboss.rusheye.retriever.Retriever#mergeProperties(org.jboss.rusheye.suite.Properties)
     */
    public Properties mergeProperties(Properties localProperties) {
        initializeRetriever();
        return sampleRetriever.mergeProperties(localProperties);
    }

    /*
     * (non-Javadoc)
     * @see org.jboss.rusheye.retriever.Retriever#setGlobalProperties(org.jboss.rusheye.suite.Properties)
     */
    public void setGlobalProperties(Properties properties) {
        initializeRetriever();
        sampleRetriever.setGlobalProperties(properties);
    }

    /*
     * (non-Javadoc)
     * @see org.jboss.rusheye.retriever.SampleRetriever#getNewSources()
     */
    public Set<String> getNewSources() {
        return null;
    }
}
