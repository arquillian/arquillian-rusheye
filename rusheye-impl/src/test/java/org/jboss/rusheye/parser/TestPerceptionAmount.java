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

import static org.jboss.rusheye.parser.VisualSuiteDefinitions.GLOBAL_DIFFERENCE_PIXEL_AMOUNT;

import java.io.IOException;

import org.jboss.rusheye.exception.ConfigurationValidationException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPerceptionAmount extends AbstractVisualSuiteDefinitionTest {
    private static final long MAX_PIXEL_AMOUNT = Long.MAX_VALUE;

    @DataProvider(name = "pixel-amount-allowed")
    public Object[][] providePixelAmountAllowed() {
        return new Object[][] { { "0%" }, { "1%" }, { "100%" }, { "0px" }, { "1px" }, { MAX_PIXEL_AMOUNT + "px" } };
    }

    @DataProvider(name = "pixel-amount-not-allowed")
    public Object[][] providePixelAmountNotAllowed() {
        return new Object[][] { { "-1%" }, { "0.1%" }, { "101%" }, { "-1px" }, { "0" }, { "1" }, { "a" }, { "null" } };
    }

    @Test(dataProvider = "pixel-amount-allowed")
    public void testGlobalDifferencePixelAmountAllowed(Object pixelAmount) throws IOException, SAXException {
        tryParsePixelAmount(pixelAmount);
    }

    @Test(dataProvider = "pixel-amount-not-allowed", expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "bad character literal: .*")
    public void testGlobalDifferencePixelAmountNotAllowed(Object pixelAmount) throws IOException, SAXException {
        tryParsePixelAmount(pixelAmount);
    }

    @Test(expectedExceptions = ConfigurationValidationException.class, expectedExceptionsMessageRegExp = "Unknown reason .*")
    public void testGlobalDifferencePixelAmountEmptyNotAllowed() throws IOException, SAXException {
        tryParsePixelAmount("");
    }

    private void tryParsePixelAmount(Object pixelAmount) throws IOException, SAXException {
        stub.perception.addElement(GLOBAL_DIFFERENCE_PIXEL_AMOUNT).addText(pixelAmount.toString());

        startWriter();
        parse();
    }
}
