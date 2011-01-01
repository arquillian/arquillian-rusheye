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
package org.jboss.rusheye.suite;

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

import org.jboss.rusheye.suite.utils.Nullify;
import org.jboss.rusheye.suite.utils.VisualSuiteResult;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ImageSource")
@XmlSeeAlso({ Mask.class, Pattern.class })
public abstract class ImageSource extends Properties {

    protected String source;

    @XmlTransient
    FutureTask<BufferedImage> future = new FutureTask<BufferedImage>(new Callable<BufferedImage>() {
        public BufferedImage call() throws Exception {
            return retrieve();
        };
    });

    @XmlAttribute
    @Nullify(VisualSuiteResult.class)
    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public void run() {
        future.run();
    }

    public abstract BufferedImage retrieve() throws Exception;

    public BufferedImage get() throws ExecutionException, InterruptedException {
        return future.get();
    }
}
