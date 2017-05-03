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
package org.arquillian.rusheye.suite;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.arquillian.rusheye.suite.annotations.Nullify;
import org.arquillian.rusheye.suite.annotations.VisualSuiteResult;

/**
 * The image source abstraction with possibility to pre-process retrieval of the image from source.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ImageSource")
@XmlSeeAlso({Mask.class, Pattern.class, Sample.class})
public abstract class ImageSource extends Properties {

    /** The source. */
    protected String source;

    /** The future task retrieving image from source. */
    @XmlTransient
    FutureTask<BufferedImage> future = new FutureTask<BufferedImage>(new Callable<BufferedImage>() {
        public BufferedImage call() throws Exception {
            return retrieve();
        }

        ;
    });

    /**
     * Retrieve the image from defined source.
     *
     * @return the buffered image
     *
     * @throws Exception
     *     the exception
     */
    public abstract BufferedImage retrieve() throws Exception;

    /**
     * Gets the source location.
     *
     * @return the source
     */
    @XmlAttribute
    @Nullify(VisualSuiteResult.class)
    public String getSource() {
        return source;
    }

    /**
     * Sets the source location.
     *
     * @param value
     *     the new source
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Run the future task retrieving image from source to prepare image for later processing.
     */
    public void run() {
        future.run();
    }

    /**
     * Gets the image prepared for processing by calling {@link #run()}.
     *
     * @return the buffered image
     *
     * @throws ExecutionException
     *     the execution exception
     * @throws InterruptedException
     *     the interrupted exception
     */
    public BufferedImage get() throws ExecutionException, InterruptedException {
        return future.get();
    }
}
