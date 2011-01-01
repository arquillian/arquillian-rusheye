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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.rusheye.suite.utils.Nullify;
import org.jboss.rusheye.suite.utils.VisualSuiteResult;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Configuration", propOrder = { "perception", "masks" })
@XmlSeeAlso({ GlobalConfiguration.class, Test.class })
public abstract class Configuration {

    protected Perception perception;
    protected List<Mask> masks;

    @Nullify(VisualSuiteResult.class)
    public Perception getPerception() {
        return perception;
    }

    public void setPerception(Perception value) {
        this.perception = value;
    }

    @XmlElement(name = "mask")
    public List<Mask> getMasks() {
        if (masks == null) {
            masks = new ArrayList<Mask>();
        }
        return this.masks;
    }

    /*
     * logic
     */
    @XmlTransient
    public final Collection<Mask> getSelectiveAlphaMasks() {
        return Collections2.filter(getMasks(), new Predicate<Mask>() {
            @Override
            public boolean apply(Mask mask) {
                return MaskType.SELECTIVE_ALPHA.equals(mask.getType());
            }
        });
    }

    @XmlTransient
    public final Collection<Mask> getIgnoreBitmapMasks() {
        return Collections2.filter(getMasks(), new Predicate<Mask>() {
            @Override
            public boolean apply(Mask mask) {
                return MaskType.IGNORE_BITMAP.equals(mask.getType());
            }
        });
    }
}
