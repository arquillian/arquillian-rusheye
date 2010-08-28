package org.jboss.lupic.core;

import java.awt.Color;

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

    public static int colorComponent(int color, int mask, int shift) {
        return (color & mask) >> shift;
    }

    public static int getR(int color) {
        return colorComponent(color, RED_MASK, RED_SHIFT);
    }

    public static int getG(int color) {
        return colorComponent(color, GREEN_MASK, GREEN_SHIFT);
    }

    public static int getB(int color) {
        return colorComponent(color, BLUE_MASK, BLUE_SHIFT);
    }

    public static int compare(int color1, int color2) {
        int d1 = Math.abs(getR(color1) - getR(color2));
        int d2 = Math.abs(getG(color1) - getG(color2));
        int d3 = Math.abs(getB(color1) - getB(color2));
        return Math.max(d1, Math.max(d2, d3));
    }

    public static Color rgb2grayscale(int color) {
        int r = getR(color);
        int g = getG(color);
        int b = getB(color);
        float gray = (0.3f * r + 0.59f * g + 0.11f * b) / 255.0f;
        return new Color(gray, gray, gray);
    }

}
