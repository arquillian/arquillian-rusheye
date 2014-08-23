/**
 * JBoss, Home of Professional Open Source Copyright ${year}, Red Hat, Inc. and individual contributors by the @authors tag. See
 * the copyright.txt in the distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.rusheye.retriever.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.jboss.rusheye.exception.RetrieverException;
import org.jboss.rusheye.suite.Properties;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class FileSampleRetriever extends AbstractSampleRetriever {

    private String commonExtension;

    @Override
    protected Set<String> getAllSources(Properties properties) throws RetrieverException {
        final File samplesDirectory = getSamplesDirectory(properties);
        Set<String> sources = findSamplesRecursively(samplesDirectory, "");
        return Collections.unmodifiableSet(sources);
    }

    private Set<String> findSamplesRecursively(File rootOfSamples, String relativePath) throws RetrieverException {
        Set<String> result = new TreeSet<String>();

        for (File sourceFile : rootOfSamples.listFiles()) {
            if (sourceFile.isDirectory()) {
                String deeperRelativePath = relativePath;
                deeperRelativePath += relativePath.isEmpty() ? "" : File.separator;
                deeperRelativePath += sourceFile.getName();
                result.addAll(findSamplesRecursively(sourceFile, deeperRelativePath));
            } else {
                String extension = StringUtils.substringAfterLast(sourceFile.getName(), ".");
                checkExtensionUniformity(extension, sourceFile.getName());

                String sourceName = StringUtils.substringBeforeLast(sourceFile.getName(), ".");
                result.add(relativePath + File.separator + sourceName + "." + extension);
            }
        }
        return result;
    }

    @Override
    protected BufferedImage loadSource(String source, Properties properties) throws RetrieverException {
        final File samplesDirectory = getSamplesDirectory(properties);

        try {
            return ImageIO.read(new File(samplesDirectory, source));
        } catch (IOException e) {
            throw new RetrieverException("can't load sample from source '" + source + "'", e);
        }
    }

    private void checkExtensionUniformity(String extension, String sourceFilename) throws RetrieverException {
        if (commonExtension == null) {
            commonExtension = extension;
        } else {
            if (!commonExtension.equals(extension)) {
                throw new RetrieverException("This retriever found the two certain file extensions (" + commonExtension + ", "
                        + extension + ") when loading image '" + sourceFilename
                        + "', it is against the contract of the extension uniformity of all loaded samples");
            }
        }
    }

    private static File getSamplesDirectory(Properties properties) {
        final File samplesDirectory = properties.getProperty("samples-directory", File.class);

        if (samplesDirectory == null) {
            throw new IllegalArgumentException(
                    "the 'samples-directory' property have to be defined in order to load list of available sources");
        }
        return samplesDirectory;
    }
}
