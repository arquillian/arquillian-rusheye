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
package org.jboss.lupic.result.writer.spooler;

import java.awt.Point;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jboss.lupic.core.ComparisonResult;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ComparisonResultSpooler implements XmlSpooler {
    XMLStreamWriter writer;
    ComparisonResult comparisonResult;

    @Override
    public void write(XMLStreamWriter writer, SpoolerContext context) throws XMLStreamException {
        this.writer = writer;
        this.comparisonResult = context.getCurrentDetail().getComparisonResult();

        writer.writeStartElement("comparison-result");
        writeArea();
        writeRectangle();
        writePixels();
        writer.writeEndElement();
    }

    private void writePixels() throws XMLStreamException {
        writePixel("total-pixels", comparisonResult.getTotalPixels());
        writePixel("masked-pixels", comparisonResult.getMaskedPixels());
        writePixel("perceptible-different-pixels", comparisonResult.getDifferentPixels());
        writePixel("global-different-pixels", comparisonResult.getSmallDifferences());
        writePixel("unperceptible-different-pixels", comparisonResult.getPerceptibleDiffs());
        writePixel("same-pixels", comparisonResult.getEqualPixels());
    }

    private void writePixel(String elementName, int pixelAmount) throws XMLStreamException {
        writer.writeStartElement(elementName);
        writer.writeCharacters(asString(pixelAmount));
        writer.writeEndElement();
    }

    private void writeArea() throws XMLStreamException {
        writer.writeEmptyElement("area");
        writer.writeAttribute("width", asString(comparisonResult.getAreaWidth()));
        writer.writeAttribute("height", asString(comparisonResult.getAreaHeight()));
    }

    private void writeRectangle() throws XMLStreamException {
        writer.writeStartElement("rectangle");
        writePoint(comparisonResult.getRectangleMin());
        writePoint(comparisonResult.getRectangleMax());
        writer.writeEndElement();
    }

    private void writePoint(Point point) throws XMLStreamException {
        writer.writeEmptyElement("point");
        writer.writeAttribute("x", asString(point.getX()));
        writer.writeAttribute("y", asString(point.getY()));
    }

    public static String asString(int i) {
        return Integer.toString(i);
    }

    public static String asString(double i) {
        return Integer.toString((int) i);
    }
}
