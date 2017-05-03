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
package org.arquillian.rusheye.oneoff;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.arquillian.rusheye.suite.Configuration;

/**
 * @author <a href="mailto:ptisnovs@redhat.com">Pavel Tisnovsky</a>
 * @version $Revision$
 */
public class OneOffConfiguration extends Configuration {
    private File firstSourceDirectory = null;
    private File secondSourceDirectory = null;
    private File diffDirectory = null;
    private File structDiffDirectory = null;
    private File maskDirectory = null;
    private File htmlOutputDirectory = null;

    public OneOffConfiguration(String[] args) {
        Log.logConfig("command line parameters: %d", args.length);
        readCommandLineParameters(args);
        printParameters();
    }

    private void printParameters() {
        Log.logConfig("first source directory:  %s", getAbsolutePath(this.firstSourceDirectory));
        Log.logConfig("second source directory: %s", getAbsolutePath(this.secondSourceDirectory));
        Log.logConfig("diffs directory:         %s", getAbsolutePath(this.diffDirectory));
        Log.logConfig("struct.diff directory:   %s", getAbsolutePath(this.structDiffDirectory));
        Log.logConfig("masks directory:         %s", getAbsolutePath(this.maskDirectory));
        Log.logConfig("html output directory:   %s", getAbsolutePath(this.htmlOutputDirectory));
    }

    private String getAbsolutePath(File directory) {
        return directory == null ? "not set" : directory.getAbsolutePath();
    }

    private void readCommandLineParameters(String[] args) {
        Map<String, String> options = resolveAllOptions(args);
        this.firstSourceDirectory = getPath(options, "s1");
        this.secondSourceDirectory = getPath(options, "s2");
        this.diffDirectory = getPath(options, "diffs");
        this.structDiffDirectory = getPath(options, "structdiffs");
        this.maskDirectory = getPath(options, "masks");
        this.htmlOutputDirectory = getPath(options, "htmlout");
    }

    private File getPath(Map<String, String> options, String optionName) {
        String fileName = options.get(optionName);
        if (fileName == null) {
            return null;
        }
        return new File(fileName);
    }

    private Map<String, String> resolveAllOptions(String[] args) {
        Map<String, String> options = new HashMap<String, String>();
        for (String arg : args) {
            String[] splittedArg = arg.split("=");
            if (splittedArg.length == 2 && splittedArg[0].length() > 2) {
                Log.logConfig("found option %s", arg);
                options.put(splittedArg[0].substring(1), splittedArg[1]);
            } else {
                Log.logWarning("unknown option %s", arg);
            }
        }
        return options;
    }

    public File getFirstSourceDirectory() {
        return this.firstSourceDirectory;
    }

    public File getSecondSourceDirectory() {
        return this.secondSourceDirectory;
    }

    public File getDiffDirectory() {
        return this.diffDirectory;
    }

    public File getMaskDirectory() {
        return this.maskDirectory;
    }

    public File getHtmlOutputDirectory() {
        return this.htmlOutputDirectory;
    }

    public File getStructDiffDirectory() {
        return this.structDiffDirectory;
    }
}
