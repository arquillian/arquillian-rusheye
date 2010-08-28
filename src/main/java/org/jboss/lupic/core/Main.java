package org.jboss.lupic.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Main {
    private int errorsCounter = 0;
    private int differentImageCounter = 0;
    private int sameImageCounter = 0;

    public void Run(String[] args) throws Exception {
        Configuration configuration = new Configuration(args);
        List<MaskImage> maskImages = ImageUtils.readMaskImages(configuration.getMaskDirectory());

        XmlWriter xmlWriter = new XmlWriter(configuration.getHtmlOutputDirectory());

        try {
            for (String imageFileName : configuration.getFirstSourceDirectory().list()) {
                testImage(configuration, maskImages, imageFileName, xmlWriter);
            }
        } catch (Exception e) {
        } finally {
            xmlWriter.printXmlFooter();
            xmlWriter.close();
        }

        Log.logStatistic("same images:      %d", sameImageCounter);
        Log.logStatistic("different images: %d", differentImageCounter);
        Log.logStatistic("errors:           %d", errorsCounter);
    }

    private void testImage(Configuration configuration, List<MaskImage> maskImages, String imageFileName,
        XmlWriter xmlWriter) {
        Log.logProcess("testing image %s", imageFileName);
        try {
            BufferedImage[] sourceImages = ImageUtils.readSourceImages(configuration.getFirstSourceDirectory(),
                configuration.getSecondSourceDirectory(), imageFileName);
            ComparisonResult comparisonResult = new ImageComparator().diffImages(imageFileName, sourceImages,
                maskImages, configuration);
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

    private void writeStructDiffImage(Configuration configuration, String imageFileName, BufferedImage[] sourceImages,
        BufferedImage diffImage) throws IOException {
        String dirName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
        File newDir = new File(configuration.getDiffDirectory(), dirName);
        Log.logProcess("creating directory %s", newDir.getAbsolutePath());
        newDir.mkdir();
        Log.logProcess("writing source and diff images to directory %s", dirName);
        ImageUtils.writeImage(sourceImages[0], newDir, "source1.png");
        ImageUtils.writeImage(sourceImages[1], newDir, "source2.png");
        ImageUtils.writeImage(diffImage, newDir, "diff.png");
    }

    private void writeDiffImage(Configuration configuration, String imageFileName, BufferedImage diffImage)
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
            new Main().Run(args);
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
