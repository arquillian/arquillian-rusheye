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
package org.arquillian.rusheye.retriever.sample;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.arquillian.rusheye.exception.NoSuchSampleException;
import org.arquillian.rusheye.exception.RetrieverException;
import org.arquillian.rusheye.retriever.AbstractRetriever;
import org.arquillian.rusheye.retriever.SampleRetriever;
import org.arquillian.rusheye.suite.Properties;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class AbstractSampleRetriever extends AbstractRetriever implements SampleRetriever {

    private SortedSet<String> allSources = null;
    private SortedSet<String> unretrievedSources = null;

    @Override
    public BufferedImage retrieve(String source, Properties localProperties) throws RetrieverException {
        Properties mergedProperties = mergeProperties(localProperties);
        
        if (allSources == null) {
            synchronized (this) {
                allSources = new TreeSet<String>(getAllSources(mergedProperties));
                unretrievedSources = new TreeSet<String>(allSources);
            }
        }

        int retries = mergedProperties.getProperty("load-source-retries", 1, Integer.class);

        if (!allSources.contains(source)) {
            throw new NoSuchSampleException("source '" + source
                + "' wasn't found when listing all of available samples");
        }

        for (int i = 0; i < retries; i++) {
            try {
                return loadSource(source, mergedProperties);
            } catch (Exception e) {
                continue;
            }
        }

        throw new RetrieverException("can't load the source '" + source + "'");
    }

    protected abstract Set<String> getAllSources(Properties properties) throws RetrieverException;

    protected abstract BufferedImage loadSource(String source, Properties properties) throws RetrieverException;

    @Override
    public Set<String> getNewSources() {
        return Collections.unmodifiableSet(unretrievedSources);
    }
}
