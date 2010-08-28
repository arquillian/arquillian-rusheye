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
package org.jboss.lupic.parser;

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

import static org.jboss.lupic.parser.VisualSuiteDefinitions.*;
import static org.testng.Assert.*;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestMask extends AbstractVisualSuiteDefinitionTest {

    static final String MASK1_ID = "mask1_id";
    static final String MASK1_SOURCE = "mask1_source";
    static final BufferedImage SAMPLE_IMAGE = new BufferedImage(1, 1, 1);

    Element masks;
    Element mask;

    @Test(expectedExceptions = SAXParseException.class)
    public void testEmptyMasksShouldRaiseException() throws IOException, SAXException {
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
    public void testMasksWithNoTypeShouldRaiseException() throws IOException, SAXException {
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
    public void testMasksWithMaskDefinedShouldPass() throws IOException, SAXException {
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        addMask(MASK1_ID, MASK1_SOURCE);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
    public void testMaskWithNoIdRaiseException() throws IOException, SAXException {
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        addMask(null, MASK1_SOURCE);
        startWriter();
        parse();
    }

    @Test
    public void testMaskWithNoSourceShouldPass() throws IOException, SAXException {
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        addMask(MASK1_ID, null);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
    public void testNonUniqueMaskIdInsideOneMasksElementRaiseException() throws IOException, SAXException {
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        addMask(MASK1_ID, MASK1_SOURCE);
        addMask(MASK1_ID, MASK1_SOURCE);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
    public void testNonUniqueMaskIdAcrossTwoMasksElementsRaiseException() throws IOException, SAXException {
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        addMask(MASK1_ID, MASK1_SOURCE);
        addMasks(MaskType.SELECTIVE_ALPHA.toXmlId());
        addMask(MASK1_ID, MASK1_SOURCE);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = SAXParseException.class)
    public void testDoubledMasksElementWithSameTypeRaiseException() throws IOException, SAXException {
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        startWriter();
        parse();
    }

    @Test
    public void testMaskFullyEquiped() throws IOException, SAXException, InterruptedException, ExecutionException {
        String retrieverImpl = AssertingRetriever.class.getName();
        stub.maskRetriever.addAttribute("class", retrieverImpl);

        addMasks(MaskType.IGNORE_BITMAP.toXmlId());
        addMask(MASK1_ID, MASK1_SOURCE);
        mask.addAttribute("vertical-align", "bottom");
        mask.addAttribute("horizontal-align", "left");
        startWriter();
        parse();

        Set<Mask> ignoreBitmapMasks = getCurrentIgnoreBitmapMasks();
        assertEquals(ignoreBitmapMasks.size(), 1);

        Mask givenMask = ignoreBitmapMasks.iterator().next();

        assertEquals(givenMask.getId(), MASK1_ID);
        assertEquals(givenMask.getHorizontalAlignment(), HorizontalAlignment.LEFT);
        assertEquals(givenMask.getVerticalAlignment(), VerticalAlignment.BOTTOM);

        // mask needs to be ran first before obtaining image
        givenMask.run();

        assertEquals(givenMask.get(), SAMPLE_IMAGE);
    }

    Set<Mask> getCurrentIgnoreBitmapMasks() {
        return handler.getVisualSuite().getGlobalConfiguration().getIgnoreBitmapMasks();
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

    void addMasks(String type) {
        masks = stub.globalConfiguration.addElement(MASKS);

        if (type != null) {
            masks.addAttribute("type", type);
        }
    }

    void addMask(String id, String source) {
        mask = masks.addElement(MASK);
        if (id != null) {
            mask.addAttribute("id", id);
        }
        if (source != null) {
            mask.addAttribute("source", source);
        }
    }

    public static class AssertingRetriever extends AbstractRetriever {
        @Override
        public BufferedImage retrieve(String source, Properties localProperties) {

            assertEquals(source, MASK1_SOURCE);
            return SAMPLE_IMAGE;
        }
    }
}
