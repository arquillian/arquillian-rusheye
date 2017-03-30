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
package org.jboss.rusheye.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import org.jboss.rusheye.oneoff.ImageUtils;
import org.jboss.rusheye.parser.ConfigurationCompiler;
import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Perception;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestImageComparison {

    static final float SMALL = 0.5f;
    static final float BIG = 200f;

    Perception perception;
    DefaultImageComparator comparator = new DefaultImageComparator();

    BufferedImage expectedDiff;

    @BeforeMethod
    public void configure() {
        perception = new Perception();
    }

    @Test
    public void testSame() {
        ComparisonResult result = diffImages("same");
        assertTrue(result.isEqualsImages());
    }

    @Test
    public void testNotSame() {
        ComparisonResult result = diffImages("not-same");
        assertFalse(result.isEqualsImages());
        assertEquals(result.getEqualPixels(), 97);
        assertEquals(result.getSmallDifferences(), 3);
        assertEquals(result.getDifferentPixels(), 0);
        assertEquals(result.getPerceptibleDiffs(), 0);
        assertEquals(result.getComparisonStatus(), "different");
        assertSame(result.getDiffImage(), expectedDiff);
    }

    @Test
    public void testPerceptible() {
        perception.setOnePixelTreshold(SMALL);
        ComparisonResult result = diffImages("perceptible");
        assertFalse(result.isEqualsImages());
        assertEquals(result.getEqualPixels(), 97);
        assertEquals(result.getSmallDifferences(), 0);
        assertEquals(result.getDifferentPixels(), 0);
        assertEquals(result.getPerceptibleDiffs(), 3);
        assertEquals(result.getComparisonStatus(), "different");
        assertSame(result.getDiffImage(), expectedDiff);
    }

    @Test
    public void testDifferent() {
        perception.setOnePixelTreshold(BIG);
        perception.setGlobalDifferenceTreshold(SMALL);
        ComparisonResult result = diffImages("different");
        assertFalse(result.isEqualsImages());
        assertEquals(result.getEqualPixels(), 97);
        assertEquals(result.getSmallDifferences(), 0);
        assertEquals(result.getDifferentPixels(), 3);
        assertEquals(result.getPerceptibleDiffs(), 0);
        assertEquals(result.getComparisonStatus(), "different");
        assertSame(result.getDiffImage(), expectedDiff);
    }

    @Test
    public void testDifferentMasked() {
        perception.setOnePixelTreshold(BIG);
        perception.setGlobalDifferenceTreshold(SMALL);
        ComparisonResult result = diffImages("different-masked");
        assertTrue(result.isEqualsImages());
        assertEquals(result.getEqualPixels(), 97);
        assertEquals(result.getSmallDifferences(), 0);
        assertEquals(result.getDifferentPixels(), 0);
        assertEquals(result.getPerceptibleDiffs(), 0);
        assertEquals(result.getMaskedPixels(), 3);
        assertEquals(result.getComparisonStatus(), "same");
        assertSame(result.getDiffImage(), expectedDiff);
    }

    @DataProvider(name = "real-samples")
    Object[][] provideRealSampleNames() {
        Object[][] provide = new Object[5][1];
        for (int i = 1; i <= provide.length; i++) {
            provide[i - 1][0] = "real-sample-" + i;
        }
        return provide;
    }

    @Test(dataProvider = "real-samples")
    public void testRealSamples(String testName) throws IOException {
        ComparisonResult result = diffImages(testName);
        assertFalse(result.isEqualsImages());
        assertSame(result.getDiffImage(), expectedDiff);
    }

    private ComparisonResult diffImages(String testName) {
        File parentDirectory = new File("./target/test-classes/image-comparator");
        File testDirectory = new File(parentDirectory, testName);

        File[] patternFiles = testDirectory.listFiles(new FilenamePrefixFilter("pattern"));
        File[] screenshotFiles = testDirectory.listFiles(new FilenamePrefixFilter("screenshot"));
        File[] maskFiles = testDirectory.listFiles(new FilenamePrefixFilter("mask"));
        File[] diffFiles = testDirectory.listFiles(new FilenamePrefixFilter("diff"));

        Set<Mask> masks = getMaskImages(maskFiles);

        assertEquals(patternFiles.length, 1);
        assertEquals(screenshotFiles.length, 1);
        assertTrue(diffFiles.length >= 0 && diffFiles.length <= 1);

        try {
            BufferedImage pattern = ImageIO.read(patternFiles[0]);
            BufferedImage screenshot = ImageIO.read(screenshotFiles[0]);
            expectedDiff = diffFiles.length == 1 ? ImageIO.read(diffFiles[0]) : null;

            ConfigurationCompiler configuration = new ConfigurationCompiler();
            org.jboss.rusheye.suite.Test test = new org.jboss.rusheye.suite.Test();
            test.setPerception(perception);
            configuration.pushConfiguration(test);

            ComparisonResult result = comparator.compare(pattern, screenshot, configuration.getPerception(), masks);

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void assertSame(BufferedImage actualDiff, BufferedImage expectedDiff) {
        Perception strictPerception = new Perception();
        strictPerception.setOnePixelTreshold(0f);
        strictPerception.setGlobalDifferenceTreshold(0f);

        ComparisonResult result = comparator.compare(actualDiff, expectedDiff, strictPerception, new HashSet<Mask>());
        assertTrue(result.isEqualsImages());
    }

    private Set<Mask> getMaskImages(File[] maskFiles) {
        try {
            Set<Mask> set = new HashSet<Mask>();
            for (File maskFile : maskFiles) {
                set.add(ImageUtils.readMaskImage(maskFile));
            }
            return set;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class FilenamePrefixFilter implements FilenameFilter {
        private String prefix;

        public FilenamePrefixFilter(String prefix) {
            super();
            this.prefix = prefix;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith(prefix);
        }
    }
}
