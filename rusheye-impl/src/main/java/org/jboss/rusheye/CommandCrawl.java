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

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jboss.rusheye.suite.MaskType;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

@Parameters(commandDescription = "Crawls the directory with images to create Visual Suite descriptor")
public class CommandCrawl extends CommandBase {

    @Parameter(converter = FileConverter.class, description = "base directory to be crawled")
    private List<File> files;
    
    private File base = new File(".");

    @Parameter(names = { "--output", "-O" }, converter = FileConverter.class,
        description = "The output of XML (default: written to stdout)")
    private File output;

    @Parameter(names = { "--force", "-f" }, description = "Force to proceed")
    private boolean force;

    @Parameter(names = { "one-pixel-treshold" }, description = "")
    private Integer onePixelTreshold;

    @Parameter(names = { "global-difference-treshold" }, description = "")
    private Integer globalDifferenceTreshold;

    @Parameter(names = { "global-difference-amount" }, description = "")
    private String globalDifferenceAmount;

    private Document document;

    @Override
    public boolean isForce() {
        return force;
    }

    public void crawl() {
        if (!files.isEmpty()) {
            base = files.get(0);
        }
        
        document = DocumentHelper.createDocument();
        addDocumentRoot();
        writeDocument();
    }

    private void writeDocument() {
        OutputFormat format = OutputFormat.createPrettyPrint();
        OutputStream out = openOutputStream();

        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            printErrorMessage(e);
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
            printErrorMessage(e);
            System.exit(7);
            return null;
        }
    }

    private void addDocumentRoot() {
        Element root = document.addElement("visual-suite");

        Element globalConfiguration = root.addElement("global-configuration");
        addImageRetriever(globalConfiguration);
        addPerception(globalConfiguration);
        addMasksByType(base, globalConfiguration);
        addTests(base, root);
    }

    private void addImageRetriever(Element globalConfiguration) {
        Element imageRetriever = globalConfiguration.addElement("image-retriever");

        imageRetriever.addAttribute("type", "org.jboss.rusheye.retriever.FileRetriever");
    }

    private void addPerception(Element base) {
        Element perception = base.addElement("perception");

        if (onePixelTreshold != null) {
            perception.addElement("one-pixel-treshold").addText(String.valueOf(onePixelTreshold));
        }
        if (globalDifferenceTreshold != null) {
            perception.addElement("global-difference-treshold").addText(String.valueOf(globalDifferenceTreshold));
        }
        if (globalDifferenceAmount != null) {
            perception.addElement("global-difference-amount").addText(globalDifferenceAmount);
        }
    }

    private void addMasksByType(File dir, Element base) {
        for (MaskType mask : MaskType.values()) {
            File maskDir = new File(dir, "masks-" + mask.value());

            if (maskDir.exists() && maskDir.isDirectory() && maskDir.listFiles().length > 0) {
                Element masks = base.addElement("masks").addAttribute("type", mask.value());
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
            for (File testFile : dir.listFiles()) {
                for (MaskType mask : MaskType.values()) {
                    if (testFile.getName().equals("masks-" + mask.value())) {
                        continue;
                    }
                }
                if (testFile.isDirectory() && testFile.listFiles().length > 0) {
                    String name = testFile.getName();

                    Element test = root.addElement("test");
                    test.addAttribute("name", name);

                    addPatterns(testFile, test);
                    addMasksByType(testFile, test);
                }
                if (testFile.isFile()) {
                    String name = substringBeforeLast(testFile.getName(), ".");

                    Element test = root.addElement("test");
                    test.addAttribute("name", name);
                    
                    String source = getRelativePath(testFile);

                    Element pattern = test.addElement("pattern");
                    pattern.addAttribute("name", name);
                    pattern.addAttribute("source", source);
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
                    pattern.addAttribute("source", source);
                }
            }
        }
    }

    private String getRelativePath(File file) {
        return substringAfter(file.getPath(), base.getPath()).replaceFirst("^/", "");
    }

    @Override
    public void validate() throws CommandValidationException {
        if (!files.isEmpty()) {
            base = files.get(0);
        }
        
        List<String> messages = constructMessages();
        messages.add(validateInputDirectory("Base", base));
        messages.add(validateOutputFile("Output", output));
        messages.add(validateOnePixelTreshold());
        messages.add(validateGlobalDifferenceTreshold());
        messages.add(validateGlobalDifferenceAmount());

        if (!messages.isEmpty()) {
            throw new CommandValidationException(StringUtils.join(messages, '\n'));
        }
    }

    private String validateOnePixelTreshold() {
        if (onePixelTreshold != null && (onePixelTreshold < 0 || onePixelTreshold > 768)) {
            return "One pixel treshold must be integer in range 0-768";
        }
        return null;
    }

    private String validateGlobalDifferenceTreshold() {
        if (globalDifferenceTreshold != null && (globalDifferenceTreshold < 0 || globalDifferenceTreshold > 768)) {
            return "Global difference treshold must be integer in range 0-768";
        }
        return null;
    }

    private String validateGlobalDifferenceAmount() {
        if (globalDifferenceAmount != null) {
            boolean matches = false;
            Pattern[] pixelAmountPatterns = new Pattern[] { Pattern.compile("\\d+px"),
                    Pattern.compile("([0-9]{1,2}|100)%") };
            for (Pattern pattern : pixelAmountPatterns) {
                if (pattern.matcher(globalDifferenceAmount).matches()) {
                    matches = true;
                    break;
                }
            }
            if (!matches) {
                return "Global difference pixel must be amount of pixels perceptually different - % of image surface or integer of pixels differ";
            }
        }
        return null;
    }
}
