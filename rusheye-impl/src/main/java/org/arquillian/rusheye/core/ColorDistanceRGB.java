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

import static org.arquillian.rusheye.core.ColorModelRGBA.getB;
import static org.arquillian.rusheye.core.ColorModelRGBA.getG;
import static org.arquillian.rusheye.core.ColorModelRGBA.getR;

public class ColorDistanceRGB implements ColorDistance {
    @Override
    public float getDistance(int color1, int color2) {
        int d1 = Math.abs(getR(color1) - getR(color2));
        int d2 = Math.abs(getG(color1) - getG(color2));
        int d3 = Math.abs(getB(color1) - getB(color2));
        return Math.max(d1, Math.max(d2, d3));
    }
}
