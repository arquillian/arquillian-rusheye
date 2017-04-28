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
package org.arquillian.rusheye.core;

import java.awt.color.ColorSpace;

public class ColorSpaceLAB extends ColorSpace {

    private static final long serialVersionUID = 1L;

    private static final double REF_X = 95.047f;
    private static final double REF_Y = 100.000f;
    private static final double REF_Z = 108.883f;

    protected ColorSpaceLAB() {
        super(ColorSpace.TYPE_Lab, 3);
    }

    public static ColorSpaceLAB getInstance() {
        return new ColorSpaceLAB();
    }

    @Override
    public float[] fromRGB(float[] rgbvalue) {
        ColorSpace cieXYZSpace = ColorSpaceXYZ.getInstance();
        float[] cieXYZ = cieXYZSpace.fromRGB(rgbvalue);
        double x = cieXYZ[0];
        double y = cieXYZ[1];
        double z = cieXYZ[2];

        x = x / REF_X;
        y = y / REF_Y;
        z = z / REF_Z;

        x = transform(x);
        y = transform(y);
        z = transform(z);

        Double l = (116.0 * y) - 16.0;
        Double a = 500.0 * (x - y);
        Double b = 200.0 * (y - z);

        float[] cieLAB = new float[cieXYZ.length];
        cieLAB[0] = l.floatValue();
        cieLAB[1] = a.floatValue();
        cieLAB[2] = b.floatValue();

        for (int i = 3; i < cieXYZ.length; i++) {
            cieXYZ[i] = cieXYZ[i];
        }

        return cieLAB;
    }

    private double transform(double base) {
        Double d = new Double(base);
        if (d > 0.008856) {
            d = Math.pow(d, 1.0 / 3);
        } else {
            d = (7.787 * d) + (16.0 / 116);
        }
        return d.floatValue();
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
