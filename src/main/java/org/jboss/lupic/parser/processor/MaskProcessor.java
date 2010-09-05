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

import org.apache.commons.lang.Validate;
import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.retriever.MaskRetriever;
import org.jboss.lupic.suite.HorizontalAlignment;
import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.VerticalAlignment;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class MaskProcessor extends Processor {
    {
        setPropertiesEnabled(true);
    }

    @Override
    public void end() {
        String id = getAttribute("id");
        String source = getAttribute("source");
        VerticalAlignment verticalAlignment = getVerticalAlignment();
        HorizontalAlignment horizontalAlignment = getHorizontalAlignment();

        Validate.notNull(id);

        if (getContext().getMaskIds().contains(id)) {
            throw new IllegalStateException("mask with id '" + id + "' already defined");
        }

        MaskRetriever maskRetriever = getVisualSuite().getGlobalConfiguration().getMaskRetriever();
        Mask mask = new Mask(id, source, getProperties(), maskRetriever, verticalAlignment, horizontalAlignment);
        getContext().getCurrentMasks().add(mask);
        getContext().getMaskIds().add(id);
    }

    VerticalAlignment getVerticalAlignment() {
        String verticalAlignment = getAttribute("vertical-align");
        return verticalAlignment == null ? null : VerticalAlignment.valueOf(verticalAlignment.toUpperCase());
    }

    HorizontalAlignment getHorizontalAlignment() {
        String horizontalAlignment = getAttribute("horizontal-align");
        return horizontalAlignment == null ? null : HorizontalAlignment.valueOf(horizontalAlignment.toUpperCase());
    }
}
