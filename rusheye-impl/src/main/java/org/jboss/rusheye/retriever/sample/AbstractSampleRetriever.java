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
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jboss.rusheye.retriever.AbstractRetriever;
import org.jboss.rusheye.retriever.RetrieverException;
import org.jboss.rusheye.suite.Properties;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class AbstractSampleRetriever extends AbstractRetriever implements SampleRetriever {

    private SortedSet<String> allSources = null;
    private SortedSet<String> unretrievedSources = null;

    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        if (allSources == null) {
            synchronized (this) {
                allSources = new TreeSet<String>(getAllSources());
                unretrievedSources = new TreeSet<String>(allSources);
            }
        }

        int retries = getProperty("load-source-retries", 1, Integer.class);

        if (!allSources.contains(source)) {
            throw new NotSuchSampleException("source '" + source
                + "' wasn't found when listing all of available samples");
        }

        for (int i = 0; i < retries; i++) {
            try {
                return loadSource(source);
            } catch (Exception e) {
                continue;
            }
        }

        throw new RetrieverException("can't load the source '" + source + "'");
    }

    protected abstract Set<String> getAllSources();

    protected abstract BufferedImage loadSource(String source) throws RetrieverException;

    @Override
    public Set<String> getNewSources() {
        return Collections.unmodifiableSet(unretrievedSources);
    }
}
