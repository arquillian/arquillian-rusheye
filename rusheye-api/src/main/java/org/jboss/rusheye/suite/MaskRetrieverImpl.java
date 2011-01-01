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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.rusheye.exception.RetrieverException;
import org.jboss.rusheye.internal.Instantiator;
import org.jboss.rusheye.retriever.MaskRetriever;

/** 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "MaskRetriever")
public class MaskRetrieverImpl extends TypeProperties implements MaskRetriever {

    @XmlTransient
    MaskRetriever maskRetriever;

    private void initializeRetriever() {
        if (maskRetriever == null) {
            if (getType() == null) {
                throw new IllegalStateException("type of the mask retriver can't be null");
            }
            maskRetriever = new Instantiator<MaskRetriever>().getInstance(getType());
            maskRetriever.setGlobalProperties(this);
        }
    }

    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        initializeRetriever();
        return maskRetriever.retrieve(source, localProperties);
    }

    public Properties mergeProperties(Properties localProperties) {
        initializeRetriever();
        return maskRetriever.mergeProperties(localProperties);
    }

    public void setGlobalProperties(Properties properties) {
        initializeRetriever();
        maskRetriever.setGlobalProperties(properties);
    }

}
