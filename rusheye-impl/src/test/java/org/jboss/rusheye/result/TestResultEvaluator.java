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
package org.jboss.rusheye.result;

import static org.jboss.rusheye.result.ResultConclusion.DIFFER;
import static org.jboss.rusheye.result.ResultConclusion.ERROR;
import static org.jboss.rusheye.result.ResultConclusion.PERCEPTUALLY_SAME;
import static org.jboss.rusheye.result.ResultConclusion.SAME;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Perception;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestResultEvaluator {

    @Spy
    ResultEvaluator evaluator = new ResultEvaluator();

    @Mock
    Perception perception;

    @Mock
    ComparisonResult comparisonResult;

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSame() {
        when(comparisonResult.isEqualsImages()).thenReturn(true);

        assertEquals(evaluator.evaluate(perception, comparisonResult), SAME);
    }

    @Test
    public void testPerceptuallySameByPercentage() {
        when(perception.getGlobalDifferencePixelAmount()).thenReturn(null);
        when(perception.getGlobalDifferencePercentage()).thenReturn((short) 5);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(100);
        when(comparisonResult.getDifferentPixels()).thenReturn(5);

        assertEquals(evaluator.evaluate(perception, comparisonResult), PERCEPTUALLY_SAME);
    }

    @Test
    public void testDifferByPercentage() {
        when(perception.getGlobalDifferencePixelAmount()).thenReturn(null);
        when(perception.getGlobalDifferencePercentage()).thenReturn((short) 8);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(100);
        when(comparisonResult.getDifferentPixels()).thenReturn(9);

        assertEquals(evaluator.evaluate(perception, comparisonResult), DIFFER);
    }

    @Test
    public void testPerceptuallySameByPixelAmount() {
        when(perception.getGlobalDifferencePixelAmount()).thenReturn((long) 6);
        when(perception.getGlobalDifferencePercentage()).thenReturn(null);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(100);
        when(comparisonResult.getDifferentPixels()).thenReturn(6);

        assertEquals(evaluator.evaluate(perception, comparisonResult), PERCEPTUALLY_SAME);
    }

    @Test
    public void testDifferByPixelAmount() {
        when(perception.getGlobalDifferencePixelAmount()).thenReturn((long) 7);
        when(perception.getGlobalDifferencePercentage()).thenReturn(null);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(100);
        when(comparisonResult.getDifferentPixels()).thenReturn(8);

        assertEquals(evaluator.evaluate(perception, comparisonResult), DIFFER);
    }

    @Test
    public void testNoGlobalDifference() {
        when(perception.getGlobalDifferencePixelAmount()).thenReturn(null);
        when(perception.getGlobalDifferencePercentage()).thenReturn(null);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(100);
        when(comparisonResult.getDifferentPixels()).thenReturn(7);

        assertEquals(evaluator.evaluate(perception, comparisonResult), ERROR);
    }

    @Test
    public void testZeroTotalPixelsProvided() {
        when(perception.getGlobalDifferencePixelAmount()).thenReturn(null);
        when(perception.getGlobalDifferencePercentage()).thenReturn((short) 5);
        when(comparisonResult.isEqualsImages()).thenReturn(false);
        when(comparisonResult.getTotalPixels()).thenReturn(0);
        when(comparisonResult.getDifferentPixels()).thenReturn(0);

        assertEquals(evaluator.evaluate(perception, comparisonResult), PERCEPTUALLY_SAME);
    }
}
