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
package org.jboss.rusheye.retriever.mask;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jboss.rusheye.exception.RetrieverException;
import org.jboss.rusheye.retriever.AbstractRetriever;
import org.jboss.rusheye.retriever.MaskRetriever;
import org.jboss.rusheye.suite.Properties;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class MaskFileRetriever extends AbstractRetriever implements MaskRetriever {

    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        Properties properties = mergeProperties(localProperties);
        
        String baseDirectory = (String) properties.getProperty("masks-directory");

        File file;

        if (baseDirectory == null) {
            file = new File(source);
        } else {
            file = new File(baseDirectory, source);
        }

        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RetrieverException("Mask file is unreachable - " + file);
        }
    }
}
