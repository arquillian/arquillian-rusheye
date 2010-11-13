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
package org.jboss.lupic.result.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class FileStorage implements ResultStorage {

    private Properties properties;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String store(Test test, Pattern pattern, BufferedImage differenceImage) {
        File directory = new File((String) properties.get("file-storage-directory"));
        File addition = new File(test.getName() + "/" + pattern.getName() + ".png");
        File file = new File(directory, addition.getPath());

        try {
            FileUtils.forceMkdir(file.getParentFile());
            ImageIO.write(differenceImage, "PNG", file);
        } catch (IOException e) {
            throw new IllegalStateException("was not able to write difference image");
        }

        return addition.getPath();
    }

    @Override
    public void end() {
    }
}
