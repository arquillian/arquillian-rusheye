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
package org.jboss.rusheye.comparison;

import java.awt.image.BufferedImage;
import java.util.Collection;
import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Perception;

/**
 * Compares pixels of images one-by-one using approximation of human perception of color spaces.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public interface ImageComparator {
    /**
     * <p>
     * Compares patternImage with sampleImage approximating human perception of color spaces.
     * </p>
     *
     * <p>
     * During comparison, uses the given settings of perception and the collection of masks.
     * </p>
     *
     * <p>
     * Implementation doesn't satisfy that it will be able to use all of the types of mask.
     * </p>
     *
     * <p>
     * Return comparison result with details of comparison.
     * </p>
     *
     * @return comparison result with details of comparison
     */
    ComparisonResult compare(BufferedImage patternImage, BufferedImage sampleImage, Perception perception,
        Collection<Mask> masks);
}
