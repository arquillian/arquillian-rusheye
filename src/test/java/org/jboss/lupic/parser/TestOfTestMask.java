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
package org.jboss.lupic.parser;

import static org.jboss.lupic.parser.VisualSuiteDefinitions.MASK;
import static org.jboss.lupic.parser.VisualSuiteDefinitions.MASKS;

import java.util.Set;

import org.jboss.lupic.suite.Mask;
import org.testng.annotations.Test;

@Test
public class TestOfTestMask extends TestMask {
    @Override
    void addMasks(String type) {
        masks = stub.defaultTest.addElement(MASKS);

        if (type != null) {
            masks.addAttribute("type", type);
        }

        moveDefaultPatternAfterMasks();
    }

    @Override
    void addMask(String id, String source) {
        mask = masks.addElement(MASK);
        if (id != null) {
            mask.addAttribute("id", id);
        }
        if (source != null) {
            mask.addAttribute("source", source);
        }
    }

    @Override
    Set<Mask> getCurrentIgnoreBitmapMasks() {
        return handler.getContext().getCurrentTest().getIgnoreBitmapMasks();
    }

    private void moveDefaultPatternAfterMasks() {
        stub.defaultTest.remove(stub.defaultPattern);
        stub.defaultTest.add(stub.defaultPattern);
    }

}
