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
package org.jboss.rusheye.retriever.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.jboss.rusheye.retriever.RetrieverException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class FileSampleRetriever extends AbstractSampleRetriever {
    @Override
    protected Set<String> getAllSources() {
        final File samplesDirectory = getProperty("sample-directory", File.class);

        if (samplesDirectory == null) {
            throw new IllegalArgumentException(
                "the 'samples' argument have to be set in order to load list of available sources");
        }

        Set<String> sources = new TreeSet<String>();
        sources.addAll(Arrays.asList(samplesDirectory.list()));

        return Collections.unmodifiableSet(sources);
    }

    @Override
    protected BufferedImage loadSource(String source) throws RetrieverException {
        try {
            return ImageIO.read(new File(source));
        } catch (IOException e) {
            throw new RetrieverException("can't load sample from source '" + source + "'", e);
        }
    }
}
