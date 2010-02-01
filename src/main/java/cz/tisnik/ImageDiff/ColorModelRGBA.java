package cz.tisnik.ImageDiff;

import java.awt.Color;

public class ColorModelRGBA
{
    final static int aMask = 0xFF000000;
    final static int rMask = 0x00FF0000;
    final static int gMask = 0x0000FF00;
    final static int bMask = 0x000000FF;
    final static int rShift = 16;
    final static int gShift = 8;
    final static int bShift = 0;

    public static int colorComponent(int color, int mask, int shift)
    {
        return (color & mask) >> shift;
    }

    public static int getR(int color)
    {
        return colorComponent(color, rMask, rShift);
    }

    public static int getG(int color)
    {
        return colorComponent(color, gMask, gShift);
    }

    public static int getB(int color)
    {
        return colorComponent(color, bMask, bShift);
    }

    public static int compare(int color1, int color2)
    {
        int d1 = Math.abs(getR(color1) - getR(color2));
        int d2 = Math.abs(getG(color1) - getG(color2));
        int d3 = Math.abs(getB(color1) - getB(color2));
        return Math.max(d1, Math.max(d2, d3));
    }

    public static Color rgb2grayscale(int color)
    {
        int r = getR(color);
        int g = getG(color);
        int b = getB(color);
        float gray = (0.3f * r + 0.59f * g + 0.11f * b) / 255.0f;
        return new Color(gray, gray, gray);
    }

}
