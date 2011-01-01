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
package org.jboss.rusheye.parser;

import java.util.LinkedList;
import java.util.List;

import org.jboss.rusheye.suite.Configuration;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Perception;

public class DefaultConfiguration extends Configuration {

    private static final List<Mask> EMPTY_MASKS = new LinkedList<Mask>();
    private static final Perception DEFAULT_PERCEPTION = new Perception();

    static {
        DEFAULT_PERCEPTION.setOnePixelTreshold(50);
        DEFAULT_PERCEPTION.setGlobalDifferenceTreshold(10);
        DEFAULT_PERCEPTION.setGlobalDifferenceAmount("0px");
    }

    {
        perception = DEFAULT_PERCEPTION;
        masks = EMPTY_MASKS;
    }

    @Override
    public void setPerception(Perception value) {
        throw new UnsupportedOperationException("Default configuration doesn't support direct assignment of perception");
    }
}
