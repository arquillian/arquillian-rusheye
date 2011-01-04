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
package org.jboss.rusheye.result.storage;

import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.jboss.rusheye.comparison.ImageComparator;
import org.jboss.rusheye.core.DefaultImageComparator;
import org.jboss.rusheye.parser.ConfigurationCompiler;
import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Pattern;
import org.jboss.rusheye.suite.Properties;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestFileStorage {

    private static final File STORAGE_DIR = new File("target/fileStorageTest");

    ImageComparator comparator = new DefaultImageComparator();

    @Mock
    org.jboss.rusheye.suite.Test test;

    @Mock
    Pattern pattern;

    @Mock
    Properties properties;

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFileStorage() throws Exception {
        when(properties.getProperty("file-storage-directory")).thenReturn(STORAGE_DIR.getPath());

        FileStorage fileStorage = new FileStorage();
        fileStorage.setProperties(properties);

        for (String color : new String[] { "green", "red" }) {
            when(test.getName()).thenReturn("test-" + color);
            when(pattern.getName()).thenReturn("pattern-" + color);

            BufferedImage expectedImage = ImageIO.read(new File("src/test/resources/parser-input/masks/" + color
                + ".png"));

            fileStorage.store(test, pattern, expectedImage);
            BufferedImage actualImage = ImageIO.read(getComplete());

            ConfigurationCompiler configurationCompiler = new ConfigurationCompiler();

            ComparisonResult result = comparator.compare(expectedImage, actualImage,
                configurationCompiler.getPerception(), new HashSet<Mask>());
            Assert.assertTrue(result.isEqualsImages());
        }

        FileUtils.deleteQuietly(STORAGE_DIR);
    }

    private File getAddition() {
        return new File(test.getName() + "." + pattern.getName() + ".png");
    }

    private File getComplete() {
        return new File(STORAGE_DIR, getAddition().getPath());
    }
}
