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
package org.jboss.rusheye.result;

import java.math.BigDecimal;

import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Perception;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ResultEvaluator {
    public ResultConclusion evaluate(Perception perception, ComparisonResult comparisonResult) {

        if (comparisonResult.isEqualsImages()) {
            return ResultConclusion.SAME;
        }

        if (perception.getGlobalDifferencePercentage() != null) {
            BigDecimal totalPixels = new BigDecimal(comparisonResult.getTotalPixels());
            BigDecimal differentPixels = new BigDecimal(comparisonResult.getDifferentPixels());

            BigDecimal ratio;
            if (comparisonResult.getTotalPixels() != 0) {
                ratio = differentPixels.multiply(new BigDecimal(100)).divide(totalPixels);
            } else {
                ratio = BigDecimal.ZERO;
            }

            int result = ratio.compareTo(new BigDecimal(perception.getGlobalDifferencePercentage()));

            if (result <= 0) {
                return ResultConclusion.PERCEPTUALLY_SAME;
            }
        } else if (perception.getGlobalDifferencePixelAmount() != null) {
            if (comparisonResult.getDifferentPixels() <= perception.getGlobalDifferencePixelAmount()) {
                return ResultConclusion.PERCEPTUALLY_SAME;
            }
        } else {
            return ResultConclusion.ERROR;
        }

        return ResultConclusion.DIFFER;
    }
}
