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
package org.jboss.rusheye.retriever;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.jboss.rusheye.core.DefaultImageComparator;
import org.jboss.rusheye.core.ImageComparator;
import org.jboss.rusheye.parser.ConfigurationCompiler;
import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Properties;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestResourceRetriever {
    ResourceRetriever resourceRetriever = new ResourceRetriever();

    @Test
    public void testResourceRetriever() throws RetrieverException, IOException {
        final String imageResource = "parser-input/masks/green.png";

        BufferedImage retrievedImage = resourceRetriever.retrieve(imageResource, new Properties());

        BufferedImage loadedImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imageResource));

        ConfigurationCompiler configurationCompiler = new ConfigurationCompiler();

        ImageComparator imageComparator = new DefaultImageComparator();
        ComparisonResult comparisonResult = imageComparator.compare(retrievedImage, loadedImage,
            configurationCompiler.getPerception(), new HashSet<Mask>());

        Assert.assertTrue(comparisonResult.isEqualsImages());

    }
}
