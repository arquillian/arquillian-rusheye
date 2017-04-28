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
package org.arquillian.rusheye.parser;

import java.util.LinkedList;
import java.util.List;
import org.arquillian.rusheye.suite.Configuration;
import org.arquillian.rusheye.suite.Mask;
import org.arquillian.rusheye.suite.Perception;

public class DefaultConfiguration extends Configuration {

    public static final float DEFAULT_ONE_PIXEL_TRESHOLD = 20f;
    public static final float DEFAULT_GLOBAL_DIFFERENCE_TRESHOLD = 1f;
    public static final int DEFAULT_GLOBAL_DIFFERENCE_AMOUNT = 0;

    private static final List<Mask> EMPTY_MASKS = new LinkedList<Mask>();
    private static final Perception DEFAULT_PERCEPTION = new Perception();

    static {
        DEFAULT_PERCEPTION.setOnePixelTreshold(DEFAULT_ONE_PIXEL_TRESHOLD);
        DEFAULT_PERCEPTION.setGlobalDifferenceTreshold(DEFAULT_GLOBAL_DIFFERENCE_TRESHOLD);
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
