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
package org.jboss.rusheye.core;

import java.awt.color.ColorSpace;

public class ColorSpaceXYZ extends ColorSpace {

    private static final long serialVersionUID = 1L;

    protected ColorSpaceXYZ() {
        super(ColorSpace.TYPE_XYZ, 3);
    }

    public static ColorSpaceXYZ getInstance() {
        return new ColorSpaceXYZ();
    }

    @Override
    public float[] fromRGB(float[] sRGB) {
        double[] c = new double[3];

        for (int i = 0; i < 3; i++) {
            c[i] = sRGB[i];
            c[i] /= 255d;
            if (c[i] <= 0.03982d) {
                c[i] /= 12.92;
            } else {
                c[i] = Math.pow(((c[i] + 0.055) / 1.055), 2.4);
            }
            c[i] *= 100;
        }

        Double x = c[0] * 0.4124 + c[1] * 0.3576 + c[2] * 0.1805;
        Double y = c[0] * 0.2126 + c[1] * 0.7152 + c[2] * 0.0722;
        Double z = c[0] * 0.0193 + c[1] * 0.1192 + c[2] * 0.9505;

        float[] cieXYZ = new float[sRGB.length];
        cieXYZ[0] = x.floatValue();
        cieXYZ[1] = y.floatValue();
        cieXYZ[2] = z.floatValue();

        for (int i = 3; i < sRGB.length; i++) {
            cieXYZ[i] = sRGB.length;
        }

        return cieXYZ;
    }

    @Override
    public float[] toRGB(float[] colorvalue) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    @Override
    public float[] toCIEXYZ(float[] colorvalue) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        throw new UnsupportedOperationException("unsupported operation");
    }
}
