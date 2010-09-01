/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
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
package org.jboss.lupic.crawling;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.OptionException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.*;
import static java.text.MessageFormat.format;
import static jargs.gnu.CmdLineParser.Option;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jboss.lupic.suite.MaskType;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class DirectoryCrawler {

    Integer onePixelTreshold;
    Integer globalDifferenceTreshold;
    String globalDifferencePixelAmount;

    Document document;
    File baseDirectory;
    File output;
    boolean force;

    public static void main(String[] args) {
        new DirectoryCrawler().crawl(args);
    }

    public void crawl(String[] args) {
        CmdLineParser parser = new CmdLineParser();

        Option oOutput = parser.addStringOption('o', "output");
        Option oForce = parser.addBooleanOption('f', "force");
        Option oOnePixelTreshold = parser.addIntegerOption("one-pixel-treshold");
        Option oGlobalDifferenceTreshold = parser.addIntegerOption("global-difference-treshold");
        Option oGlobalDifferencePixelAmount = parser.addStringOption("global-difference-pixel-amount");

        try {
            parser.parse(args);
        } catch (OptionException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }

        args = parser.getRemainingArgs();

        if (args.length != 1) {
            System.err.println("Parameter 'base_directory' missing");
            System.exit(3);
        }

        // create base directory
        baseDirectory = new File(args[0]);

        checkBaseDirectory();

        // parse force option
        this.force = (Boolean) parser.getOptionValue(oForce, Boolean.FALSE);

        // create and check output file
        String parsedOutput = (String) parser.getOptionValue(oOutput);
        this.output = (parsedOutput == null) ? null : new File(parsedOutput);

        checkOutputFile();

        // parse and check perception settings
        this.onePixelTreshold = (Integer) parser.getOptionValue(oOnePixelTreshold);
        this.globalDifferenceTreshold = (Integer) parser.getOptionValue(oGlobalDifferenceTreshold);
        this.globalDifferencePixelAmount = (String) parser.getOptionValue(oGlobalDifferencePixelAmount);

        checkPerceptionSettings();

        // create document
        document = DocumentHelper.createDocument();

        addDocumentRoot();

        // write document
        checkOutputFile();
        writeDocument();
    }

    private void checkBaseDirectory() {
        if (!baseDirectory.exists()) {
            System.err.println(format("Base directory {0} doesnt exist", baseDirectory));
            System.exit(4);
        }

        if (!baseDirectory.isDirectory()) {
            System.err.println(format("Base isn't directory: {0}", baseDirectory));
            System.exit(4);
        }
    }

    private void writeDocument() {
        OutputFormat format = OutputFormat.createPrettyPrint();
        OutputStream out = openOutputStream();

        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(7);
        }
    }

    private OutputStream openOutputStream() {
        if (this.output == null) {
            return System.out;
        }

        try {
            return new FileOutputStream(output);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(7);
            return null;
        }
    }

    private void checkOutputFile() {
        if (output != null) {
            if (output.exists() && !force) {
                System.err
                    .println("Output file already exists. Select another, delete it or try --force to overwrite.");
                System.exit(6);
            }
        }
    }

    private void checkPerceptionSettings() {
        if (onePixelTreshold != null && (onePixelTreshold < 0 || onePixelTreshold > 768)) {
            System.err.println("One pixel treshold must be integer in range 0-768");
            System.exit(5);
        }

        if (globalDifferenceTreshold != null && (globalDifferenceTreshold < 0 || globalDifferenceTreshold > 768)) {
            System.err.println("Global difference treshold must be integer in range 0-768");
            System.exit(5);
        }

        if (globalDifferencePixelAmount != null) {
            boolean matches = false;
            Pattern[] pixelAmountPatterns = new Pattern[] { Pattern.compile("\\d+px"),
                Pattern.compile("([0-9]{1,2}|100)%") };
            for (Pattern pattern : pixelAmountPatterns) {
                if (pattern.matcher(globalDifferencePixelAmount).matches()) {
                    matches = true;
                    break;
                }
            }
            if (!matches) {
                System.err
                    .println("Global difference pixel must be amount of pixels perceptually different - % of image surface or integer of pixels differ");
                System.exit(5);
            }
        }

    }

    private void addDocumentRoot() {
        Element root = document.addElement("visual-suite");

        Element globalConfiguration = root.addElement("global-configuration");
        addImageRetriever(globalConfiguration);
        addPerception(globalConfiguration);
        addMasksByType(baseDirectory, globalConfiguration);
        addTests(baseDirectory, root);
    }

    private void addImageRetriever(Element globalConfiguration) {
        Element imageRetriever = globalConfiguration.addElement("image-retriever");

        imageRetriever.addAttribute("class", "org.jboss.lupic.retriever.FileRetriever");
    }

    private void addPerception(Element base) {
        Element perception = base.addElement("perception");

        if (onePixelTreshold != null) {
            perception.addElement("one-pixel-treshold").addText(String.valueOf(onePixelTreshold));
        }
        if (globalDifferenceTreshold != null) {
            perception.addElement("global-difference-treshold").addText(String.valueOf(globalDifferenceTreshold));
        }
        if (globalDifferencePixelAmount != null) {
            perception.addElement("global-difference-pixel-amount").addText(globalDifferencePixelAmount);
        }
    }

    private void addMasksByType(File dir, Element base) {
        for (MaskType mask : MaskType.values()) {
            File maskDir = new File(dir, "masks-" + mask.toXmlId());

            if (maskDir.exists() && maskDir.isDirectory() && maskDir.listFiles().length > 0) {
                Element masks = base.addElement("masks").addAttribute("type", mask.toXmlId());
                addMasks(maskDir, masks);
            }
        }
    }

    private void addMasks(File dir, Element masks) {
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                String id = substringBeforeLast(file.getName(), ".");
                String source = getRelativePath(file);
                String info = substringAfterLast(id, "--");
                String[] infoTokens = split(info, "-");

                Element mask = masks.addElement("mask").addAttribute("id", id).addAttribute("source", source);

                for (String alignment : new String[] { "top", "bottom", "left", "right" }) {
                    String attribute = ArrayUtils.contains(new String[] { "top", "bottom" }, alignment) ? "vertical-align"
                        : "horizontal-align";
                    if (ArrayUtils.contains(infoTokens, "top")) {
                        mask.addAttribute(attribute, alignment);
                    }
                }
            }
        }
    }

    private void addTests(File dir, Element root) {
        if (dir.exists() && dir.isDirectory()) {
            for (File testDir : dir.listFiles()) {
                for (MaskType mask : MaskType.values()) {
                    if (testDir.getName().equals("masks-" + mask.toXmlId())) {
                        continue;
                    }
                }
                if (testDir.isDirectory() && testDir.listFiles().length > 0) {
                    String name = testDir.getName();

                    Element test = root.addElement("test");
                    test.addAttribute("name", name);

                    addPatterns(testDir, test);
                    addMasksByType(testDir, test);
                }
            }
        }
    }

    private void addPatterns(File dir, Element test) {
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    String name = substringBeforeLast(file.getName(), ".");
                    String source = getRelativePath(file);

                    Element pattern = test.addElement("pattern");
                    pattern.addAttribute("name", name);

                    Element image = pattern.addElement("image");
                    image.addAttribute("source", source);
                }
            }
        }
    }

    private String getRelativePath(File file) {
        return substringAfter(file.getPath(), baseDirectory.getPath()).replaceFirst("^/", "");
    }
}
