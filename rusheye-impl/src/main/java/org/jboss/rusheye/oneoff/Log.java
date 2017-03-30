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
package org.jboss.rusheye.oneoff;

/**
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @version $Revision$
 */
public final class Log {

    private Log() {
    }

    public static void log(String format, Object... o) {
        System.out.format(format, o);
        System.out.println();
    }

    public static void logMain(String format, Object... o) {
        System.out.print("main:    ");
        log(format, o);
    }

    public static void logStatistic(String format, Object... o) {
        System.out.print("stats:   ");
        log(format, o);
    }

    public static void logConfig(String format, Object... o) {
        System.out.print("config:  ");
        log(format, o);
    }

    public static void logWarning(String format, Object... o) {
        System.out.print("warning: ");
        log(format, o);
    }

    public static void logSetup(String format, Object... o) {
        System.out.print("setup:   ");
        log(format, o);
    }

    public static void logProcess(String format, Object... o) {
        System.out.print("process: ");
        log(format, o);
    }

    public static void logResult(String format, Object... o) {
        System.out.print("result:  ");
        log(format, o);
    }

    public static void logError(String format, Object... o) {
        System.out.print("ERROR:   ");
        log(format, o);
    }
}
