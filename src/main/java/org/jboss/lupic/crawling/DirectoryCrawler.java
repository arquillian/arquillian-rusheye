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

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DirectoryCrawler {

	int onePixelTreshold;
	int globalDifferenceTreshold;
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

		CmdLineParser.Option output = parser.addStringOption('o', "output");
		CmdLineParser.Option force = parser.addBooleanOption('f', "force");
		CmdLineParser.Option onePixelTreshold = parser.addIntegerOption("one-pixel-treshold");
		CmdLineParser.Option globalDifferenceTreshold = parser.addIntegerOption("global-difference-treshold");
		CmdLineParser.Option globalDifferencePixelAmount = parser.addStringOption("global-difference-pixel-amount");

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
		this.force = (Boolean) parser.getOptionValue(force, Boolean.FALSE);
		
		// create and check output file
		String parsedOutput = (String) parser.getOptionValue(output);
		this.output = (parsedOutput == null) ? null : new File(parsedOutput);
		
		checkOutputFile();
		
		// parse and check perception settings
		this.onePixelTreshold = (Integer) parser.getOptionValue(onePixelTreshold, 0);
		this.globalDifferenceTreshold = (Integer) parser.getOptionValue(globalDifferenceTreshold, 0);
		this.globalDifferencePixelAmount = (String) parser.getOptionValue(globalDifferencePixelAmount, "0px");
		
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
				System.err.println("Output file already exists. Select another, delete it or try --force to overwrite.");
				System.exit(6);
			}
		}
	}
	
	private void checkPerceptionSettings() {
		if (onePixelTreshold < 0 || onePixelTreshold > 768) {
			System.err.println("One pixel treshold must be integer in range 0-768");
			System.exit(5);
		}
		
		if (globalDifferenceTreshold < 0 || globalDifferenceTreshold > 768) {
			System.err.println("Global difference treshold must be integer in range 0-768");
			System.exit(5);
		}
		
		boolean matches = false;
		Pattern[] pixelAmountPatterns = new Pattern[] { Pattern.compile("\\d+px"), Pattern.compile("([0-9]{1,2}|100)%") };
		for (Pattern pattern : pixelAmountPatterns) {
			if (pattern.matcher(globalDifferencePixelAmount).matches()) {
				matches = true;
				break;
			}
		}
		
		if (!matches) {
			System.err.println("Global difference pixel must be amount of pixels perceptually different - % of image surface or integer of pixels differ");
			System.exit(5);
		}
	}

	private void addDocumentRoot() {
		Element root = document.addElement("visual-suite");

		Element globalConfiguration = root.addElement("global-configuration");
		addImageRetriever(globalConfiguration);
		addPerception(globalConfiguration);
		addMasksByType(baseDirectory, globalConfiguration);

		File testsDir = new File(baseDirectory, "tests");
		addTests(testsDir, root);
	}

	private void addImageRetriever(Element globalConfiguration) {
		Element imageRetriever = globalConfiguration.addElement("image-retriever");

		imageRetriever.addAttribute("class", "org.jboss.lupic.retriever.FileRetriever");
	}

	private void addPerception(Element base) {
		Element perception = base.addElement("perception");

		perception.addElement("one-pixel-treshold").addText("0");
		perception.addElement("global-difference-treshold").addText("0");
		perception.addElement("global-difference-pixel-amount").addText("0");
	}

	private void addMasksByType(File dir, Element base) {
		for (Mask mask : Mask.values()) {
			File maskDir = new File(dir, mask.getDirectory());

			if (maskDir.exists() && maskDir.isDirectory() && maskDir.listFiles().length > 0) {
				Element masks = base.addElement("masks").addAttribute("type", mask.getType());
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
				for (Mask mask : Mask.values()) {
					if (mask.getDirectory().equals(testDir.getName())) {
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

	private enum Mask {
		IGNORE_BITMAP("ignore-bitmap"), SELECTIVE_ALPHA("selective-alpha");

		private String type;

		private Mask(String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}

		public String getDirectory() {
			return "masks-" + this.type;
		}
	}

}
