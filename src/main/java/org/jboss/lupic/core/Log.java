package org.jboss.lupic.core;

public class Log
{
    public static void log(String format, Object... o)
    {
        System.out.format(format, o);
        System.out.println();
    }

    public static void logMain(String format, Object... o)
    {
        System.out.print("main:    ");
        log(format, o);
    }

    public static void logStatistic(String format, Object... o)
    {
        System.out.print("stats:   ");
        log(format, o);
    }

    public static void logConfig(String format, Object... o)
    {
        System.out.print("config:  ");
        log(format, o);
    }

    public static void logWarning(String format, Object... o)
    {
        System.out.print("warning: ");
        log(format, o);
    }

    public static void logSetup(String format, Object... o)
    {
        System.out.print("setup:   ");
        log(format, o);
    }

    public static void logProcess(String format, Object... o)
    {
        System.out.print("process: ");
        log(format, o);
    }

    public static void logResult(String format, Object... o)
    {
        System.out.print("result:  ");
        log(format, o);
    }

    public static void logError(String format, Object... o)
    {
        System.out.print("ERROR:   ");
        log(format, o);
    }

}
