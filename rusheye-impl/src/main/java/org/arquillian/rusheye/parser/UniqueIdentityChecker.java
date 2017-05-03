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
package org.arquillian.rusheye.parser;

import javax.xml.bind.Unmarshaller;
import org.arquillian.rusheye.exception.ConfigurationValidationException;
import org.arquillian.rusheye.suite.Mask;
import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Test;

public class UniqueIdentityChecker extends Unmarshaller.Listener {
    private Context context;

    UniqueIdentityChecker(Context context) {
        this.context = context;
    }

    @Override
    public void afterUnmarshal(Object target, Object parent) {
        if (target instanceof Test) {
            Test test = (Test) target;
            if (context.getTestNames().contains(test.getName())) {
                throw new ConfigurationValidationException("test's \"name\" attribute have to be unique across suite");
            }
            context.getTestNames().add(test.getName());
        }
        if (target instanceof Pattern) {
            Pattern pattern = (Pattern) target;
            if (context.getPatternNames().contains(pattern.getName())) {
                throw new ConfigurationValidationException(
                    "pattern's \"name\" attribute have to be unique across suite");
            }
            context.getPatternNames().add(pattern.getName());
        }
        if (target instanceof Mask) {
            Mask mask = (Mask) target;
            if (context.getMaskIds().contains(mask.getId())) {
                throw new ConfigurationValidationException("mask's \"id\" attribute have to be unique across suite");
            }
            context.getMaskIds().add(mask.getId());
        }
    }
}
