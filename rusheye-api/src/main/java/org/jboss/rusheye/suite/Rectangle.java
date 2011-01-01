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
package org.jboss.rusheye.suite;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "min", "max" })
public class Rectangle {
    private Point min;
    private Point max;

    @XmlElement
    @XmlJavaTypeAdapter(Adapter.class)
    public Point getMin() {
        return min;
    }

    public void setMin(Point point) {
        this.min = point;
    }

    @XmlElement
    @XmlJavaTypeAdapter(Adapter.class)
    public Point getMax() {
        return max;
    }

    public void setMax(Point point) {
        this.max = point;
    }

    private static class Adapter extends XmlAdapter<XmlPoint, Point> {
        @Override
        public XmlPoint marshal(Point v) throws Exception {
            XmlPoint r = new XmlPoint();
            r.x = new Double(v.getX()).intValue();
            r.y = new Double(v.getY()).intValue();
            return r;
        }

        @Override
        public Point unmarshal(XmlPoint v) throws Exception {
            return new Point(v.x, v.y);
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static class XmlPoint {
        @XmlAttribute
        protected int x;
        @XmlAttribute
        protected int y;
    }
}
