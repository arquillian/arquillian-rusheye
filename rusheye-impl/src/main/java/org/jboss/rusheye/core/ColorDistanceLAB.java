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

public class ColorDistanceLAB implements ColorDistance {

    ColorSpace colorSpaceLAB = new ColorSpaceLAB();

    @Override
    public float getDistance(int color1, int color2) {
        if (color1 == color2) {
            return 0;
        }
        
        float[] rgb1 = ColorModelRGBA.getRGB(color1);
        float[] rgb2 = ColorModelRGBA.getRGB(color2);

        float[] cieLab1 = colorSpaceLAB.fromRGB(rgb1);
        float[] cieLab2 = colorSpaceLAB.fromRGB(rgb2);

        return getDistance(cieLab1, cieLab2);
    }

    private float getDistance(float[] cieLab1, float[] cieLab2) {
        double suma = 0.0;
        for (int i = 0; i < 3; i++) {
            float delta = Math.abs(cieLab1[i] - cieLab2[i]);
            suma += Math.pow(delta, 2.0);
        }
        return new Double(Math.sqrt(suma)).floatValue();
    }
}
