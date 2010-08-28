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
package org.jboss.lupic.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public final class ImageUtils {
    
    private ImageUtils() {
    }

    private static BufferedImage readImage(File directory, String fileName) throws IOException {
        File imageFile = new File(directory, fileName);
        return ImageIO.read(imageFile);
    }

    public static void writeImage(BufferedImage img, File directory, String fileName) throws IOException {
        ImageIO.write(img, "png", new File(directory, fileName));
    }

    public static BufferedImage[] readSourceImages(File firstDirectory, File secondDirectory, String fileName)
        throws Exception {
        BufferedImage[] images = new BufferedImage[2];
        for (int i = 0; i < 2; i++) {
            File directory = i == 0 ? firstDirectory : secondDirectory;
            try {
                images[i] = ImageUtils.readImage(directory, fileName);
                Log.logProcess("loading image %s", new File(directory, fileName).getAbsolutePath());
            } catch (IOException e) {
                throw new Exception("Cannot load or initialize image " + fileName);
            }
        }
        return images;
    }

    public static List<MaskImage> readMaskImages(File maskDirectory) throws Exception {
        List<MaskImage> images = new ArrayList<MaskImage>();

        for (String imageFileName : maskDirectory.list()) {
            Log.logSetup("read mask image %s", imageFileName);
            images.add(new MaskImage(maskDirectory.getAbsolutePath(), imageFileName));
        }
        return images;
    }

}
