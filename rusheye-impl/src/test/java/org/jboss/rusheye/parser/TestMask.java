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
package org.jboss.rusheye.parser;

import static org.jboss.rusheye.parser.VisualSuiteDefinitions.MASK;
import static org.jboss.rusheye.suite.MaskType.IGNORE_BITMAP;
import static org.jboss.rusheye.suite.MaskType.SELECTIVE_ALPHA;
import static org.testng.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.dom4j.Element;
import org.jboss.rusheye.exception.ConfigurationValidationException;
import org.jboss.rusheye.retriever.AbstractRetriever;
import org.jboss.rusheye.retriever.MaskRetriever;
import org.jboss.rusheye.suite.HorizontalAlign;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.MaskType;
import org.jboss.rusheye.suite.Properties;
import org.jboss.rusheye.suite.VerticalAlign;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestMask extends AbstractVisualSuiteDefinitionTest {

    static final String MASK1_ID = "mask1_id";
    static final String MASK1_SOURCE = "mask1_source";
    static final BufferedImage SAMPLE_IMAGE = new BufferedImage(1, 1, 1);

    Element mask;

    @Test
    public void testMasksWithMaskDefinedShouldPass() throws IOException, SAXException {
        addMask(MASK1_ID, IGNORE_BITMAP, MASK1_SOURCE);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "element \"mask\" is missing \"id\" attribute .*")
    public void testMaskWithNoIdRaiseException() throws IOException, SAXException {
        addMask(null, IGNORE_BITMAP, MASK1_SOURCE);
        startWriter();
        parse();
    }

    @Test
    public void testMaskWithNoSourceShouldPass() throws IOException, SAXException {
        addMask(MASK1_ID, IGNORE_BITMAP, null);
        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "mask's \"id\" attribute have to be unique across suite")
    public void testNonUniqueMaskIdsRaiseException() throws IOException, SAXException {
        addMask(MASK1_ID, IGNORE_BITMAP, MASK1_SOURCE);
        addMask(MASK1_ID, SELECTIVE_ALPHA, MASK1_SOURCE);
        startWriter();
        parse();
    }

    @Test
    public void testMaskFullyEquiped() throws IOException, SAXException, InterruptedException, ExecutionException {
        String retrieverImpl = AssertingRetriever.class.getName();
        stub.maskRetriever.addAttribute("type", retrieverImpl);

        addMask(MASK1_ID, IGNORE_BITMAP, MASK1_SOURCE);
        mask.addAttribute("vertical-align", "bottom");
        mask.addAttribute("horizontal-align", "left");
        startWriter();
        parse();

        Collection<Mask> ignoreBitmapMasks = getCurrentIgnoreBitmapMasks();
        assertEquals(ignoreBitmapMasks.size(), 1);

        Mask givenMask = ignoreBitmapMasks.iterator().next();

        assertEquals(givenMask.getId(), MASK1_ID);
        assertEquals(givenMask.getHorizontalAlign(), HorizontalAlign.LEFT);
        assertEquals(givenMask.getVerticalAlign(), VerticalAlign.BOTTOM);

        // mask needs to be ran first before obtaining image
        givenMask.run();

        assertEquals(givenMask.get(), SAMPLE_IMAGE);
    }

    Collection<Mask> getCurrentIgnoreBitmapMasks() {
        return handler.getVisualSuite().getGlobalConfiguration().getIgnoreBitmapMasks();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "attribute \"vertical-align\" has a bad value: the value is not a member of the enumeration: .*")
    public void testMaskWrongVerticalAlign() throws IOException, SAXException {
        addMask(MASK1_ID, IGNORE_BITMAP, MASK1_SOURCE);
        mask.addAttribute("vertical-align", "left");
        startWriter();
        parse();
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "attribute \"horizontal-align\" has a bad value: the value is not a member of the enumeration: .*")
    public void testMaskWrongHorizontalAlign() throws IOException, SAXException {
        addMask(MASK1_ID, IGNORE_BITMAP, MASK1_SOURCE);
        mask.addAttribute("horizontal-align", "bottom");
        startWriter();
        parse();
    }

    void addMask(String id, MaskType type, String source) {
        mask = stub.globalConfiguration.addElement(MASK);
        if (id != null) {
            mask.addAttribute("id", id);
        }
        if (type != null) {
            mask.addAttribute("type", type.value());
        }
        if (source != null) {
            mask.addAttribute("source", source);
        }
    }

    public static class AssertingRetriever extends AbstractRetriever implements MaskRetriever {
        @Override
        public BufferedImage retrieve(String source, Properties localProperties) {

            assertEquals(source, MASK1_SOURCE);
            return SAMPLE_IMAGE;
        }
    }
}
