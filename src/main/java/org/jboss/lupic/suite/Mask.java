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
package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.jboss.lupic.retriever.Retriever;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Mask extends FutureTask<BufferedImage> {

    private String id;
    private Properties properties;
    private VerticalAlignment verticalAlignment;
    private HorizontalAlignment horizontalAlignment;

    public Mask(String id, final String source, final Properties maskProperties, final Retriever retriever,
        VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment) {
        super(new Callable<BufferedImage>() {
            @Override
            public BufferedImage call() throws Exception {
                return retriever.retrieve(source, maskProperties);
            }
        });
        this.id = id;
        this.properties = maskProperties;
        this.verticalAlignment = verticalAlignment;
        this.horizontalAlignment = horizontalAlignment;
    }

    public String getId() {
        return id;
    }

    public Properties getProperties() {
        return properties;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Mask other = (Mask) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
