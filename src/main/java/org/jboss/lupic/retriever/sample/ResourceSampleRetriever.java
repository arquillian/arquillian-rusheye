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
package org.jboss.lupic.retriever.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jboss.lupic.retriever.AbstractRetriever;
import org.jboss.lupic.retriever.ResourceRetriever;
import org.jboss.lupic.retriever.RetrieverException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ResourceSampleRetriever extends AbstractRetriever implements SampleRetriever {

    public static final String RESOURCES_LOCATION = "resources-location";
    public static final String RESOURCE_EXTENSION = "resource-suffix";

    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        final File resourcesLocation = getProperty(RESOURCES_LOCATION, File.class);
        final String resourceExtension = getProperty(RESOURCE_EXTENSION, String.class);

        if (resourcesLocation == null) {
            throw new IllegalStateException("sample retriever property '" + RESOURCES_LOCATION
                + "' have to be set in order to obtain resources");
        }

        if (resourceExtension == null) {
            throw new IllegalStateException("sample retriever property '" + RESOURCE_EXTENSION
                + "' have to be set in order to obtain resources");
        }

        final File sourceFile = new File(resourcesLocation, source + resourceExtension);

        URL resourceURL = this.getClass().getClassLoader().getResource(sourceFile.getPath());
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(resourceURL);
        } catch (IOException e) {
            throw new RetrieverException(ResourceRetriever.class.getSimpleName()
                + " wasn't able to retrieve image from resourceURL '" + resourceURL + "'", e);
        }

        return bufferedImage;
    }

    @Override
    public Set<String> getNewSources() {
        return new HashSet<String>();
    }
}
