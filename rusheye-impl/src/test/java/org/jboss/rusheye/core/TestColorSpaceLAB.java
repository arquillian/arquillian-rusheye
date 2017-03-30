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

import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.jboss.rusheye.core.TestColorSpaceLAB.RGB.rgb;

public class TestColorSpaceLAB {

    ColorSpaceLAB cieLABSpace = ColorSpaceLAB.getInstance();

    RGB[] data = new RGB[] {rgb(0, 0, 0).lab(0, 0, 0), rgb(0, 0, 255).lab(32.303f, 79.197f, -107.864f),
        rgb(0, 255, 0).lab(87.737f, -86.185f, 83.181f), rgb(255, 0, 0).lab(53.233f, 80.109f, 67.220f),
        rgb(0, 255, 255).lab(91.117f, -48.080f, -14.138f), rgb(255, 0, 255).lab(60.320f, 98.254f, -60.843f),
        rgb(255, 255, 0).lab(97.138f, -21.556f, 94.482f), rgb(255, 255, 255).lab(100.000f, 0.005f, -0.010f)};

    private static float roundTo3(float number) {
        number *= 1000;
        number = Math.round(number);
        number /= 1000;
        return number;
    }

    @DataProvider
    public Object[][] getData() {
        Object[][] result = new Object[data.length][1];
        for (int i = 0; i < data.length; i++) {
            result[i][0] = data[i];
        }
        return result;
    }

    @Test(dataProvider = "getData")
    public void testColorSpaceLAB(RGB value) {
        float[] cieLAB = cieLABSpace.fromRGB(value.rgb);
        value.compare(cieLAB);
    }

    public static class RGB {
        float[] rgb;
        float[] lab;

        public static RGB rgb(float r, float g, float b) {
            RGB rgb = new RGB();
            rgb.rgb = new float[] {r, g, b};
            return rgb;
        }

        public RGB lab(float l, float a, float b) {
            this.lab = new float[] {l, a, b};
            return this;
        }

        public void compare(float[] lab) {
            for (int i = 0; i < 3; i++) {
                Assert.assertEquals(Float.toString(roundTo3(lab[i])), Float.toString(this.lab[i]),
                    String.valueOf("Lab".charAt(i)));
            }
        }

        @Override
        public String toString() {
            return Arrays.toString(rgb);
        }
    }
}
