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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jboss.rusheye.internal.Instantiator;
import org.jboss.rusheye.listener.SuiteListener;
import org.jboss.rusheye.parser.Parser;
import org.jboss.rusheye.suite.Properties;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

@Parameters(commandDescription = "Parse the Visual Suite descriptor in order to run comparison process")
public class CommandParse extends CommandBase {

    @Parameter(required = true, arity = 1, description = "The Visual Suite descriptor", converter = FileConverter.class)
    private List<File> files;

    @Parameter(names = { "--listener", "-L" }, description = "Main listener used to proceed comparison",
        converter = SuiteListenerConverter.class)
    private SuiteListener suiteListener;

    @Parameter(names = { "-D" }, description = "Defines properties to listeners proceeding comparison")
    private List<String> definitions;

    @Parameter(names = { "--output", "-O" }, description = "Visual Suite Result descriptor output filename")
    private File output;

    @Parameter(names = { "--force", "-f" }, description = "Force to proceed")
    private boolean force = false;

    private File input;
    private Properties properties = new Properties();

    public void parse() {
        try {
            Parser parser = new Parser();
            parser.setProperties(properties);
            if (suiteListener != null) {
                parser.registerListener(suiteListener);
            }
            parser.parseFile(input);
        } catch (Exception e) {
            printErrorMessage(e);
            System.exit(100);
        }
    }

    @Override
    public boolean isForce() {
        return force;
    }

    @Override
    public void initialize() {
        input = files.get(0);

        if (definitions != null) {
            for (String definition : definitions) {
                String key = StringUtils.substringBefore(definition, "=");
                String value = StringUtils.substringAfter(definition, "=");
                properties.setProperty(key, value);
            }
        }
    }

    @Override
    public void validate() throws CommandValidationException {
        List<String> messages = constructMessages();
        messages.add(validateInputFile("Visual Suite", input));
        messages.add(validateOutputFile("Output", output));

        if (!messages.isEmpty()) {
            throw new CommandValidationException(StringUtils.join(messages, '\n'));
        }
    }

    public class SuiteListenerConverter implements IStringConverter<SuiteListener> {
        @Override
        public SuiteListener convert(String type) {
            return new Instantiator<SuiteListener>().getInstance(type);
        }
    }
}
