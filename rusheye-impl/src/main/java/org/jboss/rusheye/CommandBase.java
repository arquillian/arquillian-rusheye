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
package org.jboss.rusheye;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class CommandBase {

    @Parameter(names = { "-h", "--help" })
    private Boolean help;

    @Parameter(names = { "--debug" })
    private Boolean debug;

    public boolean isHelp() {
        return help == Boolean.TRUE;
    }

    public boolean isDebug() {
        return debug == Boolean.TRUE;
    }

    public void initialize() {
    }

    public void validate() throws CommandValidationException {
    }

    protected boolean isForce() {
        return false;
    }

    protected void printErrorMessage(Exception e) {
        if (isDebug()) {
            e.printStackTrace();
        } else {
            System.err.println(e.getMessage());
        }
    }

    protected String validateInputFile(String name, File file) {
        if (file != null) {
            if (!file.exists()) {
                return name + " file '" + file.getPath() + "' does not exists";
            }
            if (!file.canRead()) {
                return name + " file '" + file.getPath() + "' can't be read";
            }
        }
        return null;
    }

    protected String validateOutputFile(String name, File file) {
        if (file != null) {
            if (file.exists() && !isForce()) {
                return name + " file '" + file.getPath() + "' already exists (use --force/-f to overwrite)";
            }
            if (file.exists() && !file.canWrite()) {
                return name + " file '" + file.getPath() + "' can't be written";
            }
        }
        return null;
    }

    protected String validateInputDirectory(String name, File directory) {
        if (directory != null) {
            if (!directory.exists()) {
                return name + " directory '" + directory.getPath() + "' doesnt exist)";
            }

            if (!directory.isDirectory()) {
                return name + " directory '" + directory.getPath() + "' isn't directory)";
            }
        }
        return null;
    }

    @SuppressWarnings("serial")
    protected List<String> constructMessages() {
        return new LinkedList<String>() {
            public boolean add(String e) {
                if (e == null) {
                    return false;
                }
                return super.add(e);
            };
        };
    }
}
