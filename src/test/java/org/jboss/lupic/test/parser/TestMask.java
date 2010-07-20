package org.jboss.lupic.test.parser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.dom4j.Element;
import org.jboss.lupic.retriever.AbstractRetriever;
import org.jboss.lupic.suite.HorizontalAlignment;
import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.MaskType;
import org.jboss.lupic.suite.VerticalAlignment;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.jboss.lupic.test.parser.VisualSuiteDefinitions.*;
import static org.testng.Assert.*;

public class TestMask extends AbstractVisualSuiteDefinitionTest {

	private static final String MASK1_ID = "mask1_id";
	private static final String MASK1_SOURCE = "mask1_source";
	Element masks;
	Element mask;

	@Test(expectedExceptions = SAXParseException.class)
	public void testEmptyMasksShouldRaiseException() throws IOException,
			SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testMasksWithNoTypeShouldRaiseException() throws IOException,
			SAXException {
		addMasks(null);
		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testMasksWithNotExistingType() throws IOException, SAXException {
		addMasks("not-existing-type");
		startWriter();
		parse();
	}

	@Test
	public void testMasksWithMaskDefinedShouldPass() throws IOException,
			SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(MASK1_ID, MASK1_SOURCE);
		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testMaskWithNoIdRaiseException() throws IOException,
			SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(null, MASK1_SOURCE);
		startWriter();
		parse();
	}

	@Test
	public void testMaskWithNoSourceShouldPass() throws IOException,
			SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(MASK1_ID, null);
		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testNonUniqueMaskIdInsideOneMasksElementRaiseException()
			throws IOException, SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(MASK1_ID, MASK1_SOURCE);
		addMask(MASK1_ID, MASK1_SOURCE);
		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testNonUniqueMaskIdAcrossTwoMasksElementsRaiseException()
			throws IOException, SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(MASK1_ID, MASK1_SOURCE);
		addMasks(MaskType.SELECTIVE_ALPHA.toXmlId());
		addMask(MASK1_ID, MASK1_SOURCE);
		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testDoubledMasksElementWithSameTypeRaiseException()
			throws IOException, SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		startWriter();
		parse();
	}

	@Test
	public void testMaskFullyEquiped() throws IOException, SAXException,
			InterruptedException, ExecutionException {
		String retrieverImpl = AssertingRetriever.class.getName();
		stub.imageRetriever.addAttribute("class", retrieverImpl);

		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(MASK1_ID, MASK1_SOURCE);
		mask.addAttribute("vertical-align", "bottom");
		mask.addAttribute("horizontal-align", "left");
		startWriter();
		parse();

		Set<Mask> ignoreBitmapMasks = handler.getVisualSuite()
				.getGlobalConfiguration().getIgnoreBitmapMasks();
		assertEquals(ignoreBitmapMasks.size(), 1);

		Mask givenMask = ignoreBitmapMasks.iterator().next();

		assertEquals(givenMask.getId(), MASK1_ID);
		assertEquals(givenMask.getHorizontalAlignment(),
				HorizontalAlignment.LEFT);
		assertEquals(givenMask.getVerticalAlignment(), VerticalAlignment.BOTTOM);

		// mask needs to be ran first before obtaining image
		givenMask.run();

		assertEquals(givenMask.get(), image);
	}

	private final static BufferedImage image = new BufferedImage(1, 1, 1);

	public static class AssertingRetriever extends AbstractRetriever {
		@Override
		public BufferedImage retrieve(String source, Properties localProperties) {

			assertEquals(source, MASK1_SOURCE);
			return image;
		}
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testMaskWrongVerticalAlign() throws IOException, SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(MASK1_ID, MASK1_SOURCE);
		mask.addAttribute("vertical-align", "left");
		startWriter();
		parse();
	}

	@Test(expectedExceptions = SAXParseException.class)
	public void testMaskWrongHorizontalAlign() throws IOException, SAXException {
		addMasks(MaskType.IGNORE_BITMAP.toXmlId());
		addMask(MASK1_ID, MASK1_SOURCE);
		mask.addAttribute("horizontal-align", "bottom");
		startWriter();
		parse();
	}

	private void addMasks(String type) {
		masks = stub.globalConfiguration.addElement(MASKS);

		if (type != null) {
			masks.addAttribute("type", type);
		}
	}

	private void addMask(String id, String source) {
		mask = masks.addElement(MASK);
		if (id != null) {
			mask.addAttribute("id", id);
		}
		if (source != null) {
			mask.addAttribute("source", source);
		}
	}
}
