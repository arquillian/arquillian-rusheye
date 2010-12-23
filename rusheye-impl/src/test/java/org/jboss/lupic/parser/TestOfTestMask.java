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

import java.util.Collection;

import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.MaskType;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@Test
public class TestOfTestMask extends TestMask {

    @Override
    void addMask(String id, MaskType type, String source) {
        mask = stub.defaultTest.addElement(MASK);
        if (id != null) {
            mask.addAttribute("id", id);
        }
        if (type != null) {
            mask.addAttribute("type", type.value());
        }
        if (source != null) {
            mask.addAttribute("source", source);
        }
        moveDefaultPatternAfterMasks();
    }

    @Override
    Collection<Mask> getCurrentIgnoreBitmapMasks() {
        return handler.getContext().getCurrentTest().getIgnoreBitmapMasks();
    }

    private void moveDefaultPatternAfterMasks() {
        stub.defaultTest.remove(stub.defaultPattern);
        stub.defaultTest.add(stub.defaultPattern);
    }

}
