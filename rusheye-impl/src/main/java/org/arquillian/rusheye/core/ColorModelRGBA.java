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

import java.awt.Color;

/**
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @version $Revision$
 */
public final class ColorModelRGBA {
    static final int ALPHA_MASK = 0xFF000000;
    static final int RED_MASK = 0x00FF0000;
    static final int GREEN_MASK = 0x0000FF00;
    static final int BLUE_MASK = 0x000000FF;
    static final int RED_SHIFT = 16;
    static final int GREEN_SHIFT = 8;
    static final int BLUE_SHIFT = 0;

    private ColorModelRGBA() {
    }

    static int getR(int color) {
        return colorComponent(color, RED_MASK, RED_SHIFT);
    }

    static int getG(int color) {
        return colorComponent(color, GREEN_MASK, GREEN_SHIFT);
    }

    static int getB(int color) {
        return colorComponent(color, BLUE_MASK, BLUE_SHIFT);
    }

    static Color rgb2grayscale(int color) {
        int r = getR(color);
        int g = getG(color);
        int b = getB(color);
        float gray = (0.3f * r + 0.59f * g + 0.11f * b) / 255.0f;
        return new Color(gray, gray, gray);
    }

    static float[] getRGB(int color) {
        return new float[] {getR(color), getG(color), getB(color)};
    }

    private static int colorComponent(int color, int mask, int shift) {
        return (color & mask) >> shift;
    }
}
