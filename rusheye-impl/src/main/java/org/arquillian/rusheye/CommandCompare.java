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
package org.arquillian.rusheye;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.lang.StringUtils;
import org.arquillian.rusheye.core.DefaultImageComparator;
import org.arquillian.rusheye.parser.DefaultConfiguration;
import org.arquillian.rusheye.result.ResultEvaluator;
import org.arquillian.rusheye.suite.ComparisonResult;
import org.arquillian.rusheye.suite.Configuration;
import org.arquillian.rusheye.suite.ResultConclusion;

@Parameters(commandDescription = "Compares two images with given perceptional settings and masks")
public class CommandCompare extends CommandBase {

    @Parameter(required = true, arity = 2, converter = FileConverter.class,
        description = "<Pattern> and <Sample> to be compared")
    private List<File> files;

    @Parameter(names = {"--output", "-O"}, converter = FileConverter.class,
        description = "The difference image as result of comparison")
    private File output;

    @Parameter(names = {"--force", "-f"}, description = "Force to proceed")
    private boolean force = false;

    private ComparisonResult result;
    private Configuration configuration;
    private ResultConclusion conclusion;

    private File getPatternFile() {
        return files.get(0);
    }

    private File getSampleFile() {
        return files.get(1);
    }

    @Override
    protected boolean isForce() {
        return force;
    }

    public void compare() {
        try {
            BufferedImage pattern = loadImage(getPatternFile());
            BufferedImage sample = loadImage(getSampleFile());

            configuration = new DefaultConfiguration();
            result = new DefaultImageComparator().compare(pattern, sample, configuration.getPerception(),
                configuration.getMasks());
            conclusion = new ResultEvaluator().evaluate(configuration.getPerception(), result);
        } catch (Exception e) {
            printErrorMessage(e);
            System.exit(3);
        }
    }

    public void printResult() {
        System.out.println("Result: " + conclusion);
    }

    public boolean isOutputSet() {
        return output != null;
    }

    public void writeDifferenceImage() {
        try {
            ImageIO.write(result.getDiffImage(), "PNG", output);
        } catch (Exception e) {
            printErrorMessage(e);
            System.exit(3);
        }
    }

    @Override
    public void validate() throws CommandValidationException {
        List<String> messages = constructMessages();
        messages.add(validateInputFile("Pattern", getPatternFile()));
        messages.add(validateInputFile("Sample", getSampleFile()));
        messages.add(validateOutputFile("Output", output));

        if (!messages.isEmpty()) {
            throw new CommandValidationException(StringUtils.join(messages, '\n'));
        }
    }

    private BufferedImage loadImage(File file) throws CommandProcessingException {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new CommandProcessingException("Failed to load image from file '" + getSampleFile().getPath() + "': "
                + e.getMessage(), e);
        }
    }
}
