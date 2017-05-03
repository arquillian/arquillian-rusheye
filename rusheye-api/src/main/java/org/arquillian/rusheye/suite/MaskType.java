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
package org.arquillian.rusheye.suite;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration of known mask types.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@XmlType(name = "MaskType")
@XmlEnum
public enum MaskType {

    /**
     * The type selective alpha stands for image with alpha channel defining pixels which should be masked.
     */
    @XmlEnumValue("selective-alpha")
    SELECTIVE_ALPHA("selective-alpha"),

    /**
     * The type ignore bitmap stands for image which defines image, which should be identified in scope of
     * sample/pattern to mask such area.
     */
    @XmlEnumValue("ignore-bitmap")
    IGNORE_BITMAP("ignore-bitmap");

    private final String value;

    private MaskType(String v) {
        value = v;
    }

    public static MaskType fromValue(String v) {
        for (MaskType c : MaskType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String value() {
        return value;
    }
}
