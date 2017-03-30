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
import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.jboss.rusheye.retriever.SampleRetriever;

/**
 * The sample as the source of comparison process, to be compared againts patterns.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Sample")
public class Sample extends ImageSource {

    /**
     * Sample retriever - needs to be injected from outside to let sample work properly.
     */
    @Resource
    @XmlTransient
    private SampleRetriever sampleRetriever;

    @Override
    public BufferedImage retrieve() throws Exception {
        return sampleRetriever.retrieve(source, this);
    }

    /**
     * Sets the sample retriever.
     *
     * @param sampleRetriever
     *     sample retriever to be associated with this sample
     */
    public void setSampleRetriever(SampleRetriever sampleRetriever) {
        this.sampleRetriever = sampleRetriever;
    }
}
