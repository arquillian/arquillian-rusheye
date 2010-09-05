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
package org.jboss.lupic.oneoff;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.jboss.lupic.core.ComparisonResult;
import org.jboss.lupic.core.ImageComparator;
import org.jboss.lupic.suite.Mask;

/**
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @version $Revision$
 */
public class Main {

    private int errorsCounter = 0;
    private int differentImageCounter = 0;
    private int sameImageCounter = 0;

    public void run(String[] args) throws Exception {
        OneOffConfiguration configuration = new OneOffConfiguration(args);
        Set<Mask> maskImages = ImageUtils.readMaskImages(configuration.getMaskDirectory());

        XmlWriter xmlWriter = new XmlWriter(configuration.getHtmlOutputDirectory());

        try {
            for (String imageFileName : configuration.getFirstSourceDirectory().list()) {
                testImage(configuration, maskImages, imageFileName, xmlWriter);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            xmlWriter.printXmlFooter();
            xmlWriter.close();
        }

        Log.logStatistic("same images:      %d", sameImageCounter);
        Log.logStatistic("different images: %d", differentImageCounter);
        Log.logStatistic("errors:           %d", errorsCounter);
    }

    private void testImage(OneOffConfiguration configuration, Set<Mask> maskImages, String imageFileName,
        XmlWriter xmlWriter) {
        Log.logProcess("testing image %s", imageFileName);
        try {
            BufferedImage[] sourceImages = ImageUtils.readSourceImages(configuration.getFirstSourceDirectory(),
                configuration.getSecondSourceDirectory(), imageFileName);
            ComparisonResult comparisonResult = new ImageComparator().compare(sourceImages[0], sourceImages[1],
                configuration.getPerception(), maskImages);
            if (!comparisonResult.isEqualsImages()) {
                differentImageCounter++;
                Log.logResult("DIFFER: %s", imageFileName);
                if (configuration.getDiffDirectory() != null) {
                    writeDiffImage(configuration, imageFileName, comparisonResult.getDiffImage());
                }
                if (configuration.getStructDiffDirectory() != null) {
                    writeStructDiffImage(configuration, imageFileName, sourceImages, comparisonResult.getDiffImage());
                }
            } else {
                sameImageCounter++;
                Log.logResult("SAME: %s", imageFileName);
            }
            xmlWriter.printResultForOneImage(configuration, imageFileName, sourceImages, comparisonResult);
        } catch (Exception e) {
            errorsCounter++;
            Log.logError("%s", e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    private void writeStructDiffImage(OneOffConfiguration configuration, String imageFileName,
        BufferedImage[] sourceImages, BufferedImage diffImage) throws IOException {
        String dirName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
        File newDir = new File(configuration.getDiffDirectory(), dirName);
        Log.logProcess("creating directory %s", newDir.getAbsolutePath());
        newDir.mkdir();
        Log.logProcess("writing source and diff images to directory %s", dirName);
        ImageUtils.writeImage(sourceImages[0], newDir, "source1.png");
        ImageUtils.writeImage(sourceImages[1], newDir, "source2.png");
        ImageUtils.writeImage(diffImage, newDir, "diff.png");
    }

    private void writeDiffImage(OneOffConfiguration configuration, String imageFileName, BufferedImage diffImage)
        throws IOException {
        ImageUtils.writeImage(diffImage, configuration.getDiffDirectory(), imageFileName);
        Log.logProcess("writing diff image %s", imageFileName);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        try {
            Log.logMain("started at %s", new Date().toString());
            new Main().run(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            long t2 = System.currentTimeMillis();
            Log.logStatistic("run duration:     %ds", (t2 - t1) / 1000);
            Log.logMain("finished at %s", new Date().toString());
        }
    }

}
