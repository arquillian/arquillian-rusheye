/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
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
package org.jboss.lupic.retriever;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ResourceRetriever extends AbstractRetriever implements PatternRetriever, MaskRetriever {
    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        URL resourceURL = this.getClass().getClassLoader().getResource(source);

        if (resourceURL == null) {
            throw new RetrieverException(this.getClass().getSimpleName() + " wasn't able to retrieve image source '"
                + source + "' - given source doesn't exist");
        }

        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(resourceURL);
        } catch (IOException e) {
            throw new RetrieverException(this.getClass().getSimpleName() + " wasn't able to retrieve image source '"
                + source + "' from resourceURL '" + resourceURL + "'", e);
        }

        return bufferedImage;
    }
}
