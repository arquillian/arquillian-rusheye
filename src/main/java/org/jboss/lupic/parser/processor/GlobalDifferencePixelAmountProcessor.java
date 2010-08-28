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
package org.jboss.lupic.parser.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.suite.Perception;

public class GlobalDifferencePixelAmountProcessor extends Processor {

    Pattern percentPattern = Pattern.compile("([0-9]{1,2}|100)%");
    Pattern pixelPattern = Pattern.compile("(\\d)+px");

    @Override
    public void process(String content) {
        Matcher matcher;
        Perception perception = getContext().getCurrentConfiguration().getPerception();
        for (Pattern pattern : new Pattern[] { percentPattern, pixelPattern }) {
            matcher = pattern.matcher(content);
            if (matcher.lookingAt()) {
                if (pattern == percentPattern) {
                    perception.setGlobalDifferencePercentage(Short.valueOf(matcher.group(1)));
                } else {
                    perception.setGlobalDifferencePixelAmount(Long.valueOf(matcher.group(1)));
                }
                return;
            }
        }
        throw new IllegalStateException();
    }
}
