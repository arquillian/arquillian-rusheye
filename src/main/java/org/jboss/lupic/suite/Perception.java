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

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Perception {
    Short onePixelTreshold = null;
    Short globalDifferenceTreshold = null;
    Long globalDifferencePixelAmount = null;
    Short globalDifferencePercentage = null;

    public Perception() {
    }

    public Short getOnePixelTreshold() {
        return onePixelTreshold;
    }

    public void setOnePixelTreshold(short onePixelTreshold) {
        this.onePixelTreshold = onePixelTreshold;
    }

    public Short getGlobalDifferenceTreshold() {
        return globalDifferenceTreshold;
    }

    public void setGlobalDifferenceTreshold(short globalDifferenceTreshold) {
        this.globalDifferenceTreshold = globalDifferenceTreshold;
    }

    public Long getGlobalDifferencePixelAmount() {
        return globalDifferencePixelAmount;
    }

    public void setGlobalDifferencePixelAmount(long globalDifferencePixelAmount) {
        this.globalDifferencePercentage = null;
        this.globalDifferencePixelAmount = globalDifferencePixelAmount;
    }

    public Short getGlobalDifferencePercentage() {
        return globalDifferencePercentage;
    }

    public void setGlobalDifferencePercentage(short globalDifferencePercentage) {
        this.globalDifferencePixelAmount = null;
        this.globalDifferencePercentage = globalDifferencePercentage;
    }

    public void setDefaultValuesForUnset() {
        if (onePixelTreshold == null) {
            onePixelTreshold = 50;
        }
        if (globalDifferenceTreshold == null) {
            globalDifferenceTreshold = 10;
        }
        if (globalDifferencePercentage == null && globalDifferencePixelAmount == null) {
            globalDifferencePixelAmount = 0L;
        }
    }

    public void setValuesFromParent(Perception parent) {
        this.onePixelTreshold = parent.onePixelTreshold;
        this.globalDifferenceTreshold = parent.globalDifferenceTreshold;
        if (parent.globalDifferencePixelAmount != null) {
            this.setGlobalDifferencePixelAmount(parent.globalDifferencePixelAmount);
        }
        if (parent.globalDifferencePercentage != null) {
            this.setGlobalDifferencePercentage(parent.globalDifferencePercentage);
        }
    }
}
