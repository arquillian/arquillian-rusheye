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
package org.jboss.lupic.suite;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Configuration {
    Perception perception = new Perception();
    Set<Mask> ignoreBitmapMasks = new LinkedHashSet<Mask>();
    Set<Mask> selectiveAlphaMasks = new LinkedHashSet<Mask>();

    public Perception getPerception() {
        return perception;
    }

    public void setPerception(Perception perception) {
        this.perception = perception;
    }

    public Set<Mask> getIgnoreBitmapMasks() {
        return ignoreBitmapMasks;
    }

    public void setIgnoreBitmapMasks(Set<Mask> ignoreBitmapMasks) {
        this.ignoreBitmapMasks = ignoreBitmapMasks;
    }

    public Set<Mask> getSelectiveAlphaMasks() {
        return selectiveAlphaMasks;
    }

    public void setSelectiveAlphaMasks(Set<Mask> selectiveAlphaMasks) {
        this.selectiveAlphaMasks = selectiveAlphaMasks;
    }

    public void setDefaultValuesForUnset() {
        perception.setDefaultValuesForUnset();
    }

    public void setValuesFromParent(Configuration parent) {
        perception.setValuesFromParent(parent.getPerception());
        ignoreBitmapMasks.addAll(parent.getIgnoreBitmapMasks());
        selectiveAlphaMasks.addAll(parent.getSelectiveAlphaMasks());
    }
}
