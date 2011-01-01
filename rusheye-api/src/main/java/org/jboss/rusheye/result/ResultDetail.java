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

import org.jboss.rusheye.suite.ComparisonResult;
import org.jboss.rusheye.suite.Pattern;

/**
 * The detailed result of the comparison process for given pattern.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ResultDetail {
    
    /** The pattern. */
    private Pattern pattern;
    
    /** The location. */
    private String location;
    
    /** The comparison result. */
    private ComparisonResult comparisonResult;
    
    /** The conclusion. */
    private ResultConclusion conclusion;

    /**
     * Gets the pattern.
     *
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Sets the pattern.
     *
     * @param pattern the new pattern
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the comparison result.
     *
     * @return the comparison result
     */
    public ComparisonResult getComparisonResult() {
        return comparisonResult;
    }

    /**
     * Sets the comparison result.
     *
     * @param comparisonResult the new comparison result
     */
    public void setComparisonResult(ComparisonResult comparisonResult) {
        this.comparisonResult = comparisonResult;
    }

    /**
     * Gets the conclusion.
     *
     * @return the conclusion
     */
    public ResultConclusion getConclusion() {
        return conclusion;
    }

    /**
     * Sets the conclusion.
     *
     * @param conclusion the new conclusion
     */
    public void setConclusion(ResultConclusion conclusion) {
        this.conclusion = conclusion;
    }

}