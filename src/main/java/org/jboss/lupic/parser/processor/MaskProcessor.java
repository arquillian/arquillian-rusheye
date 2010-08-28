package org.jboss.lupic.parser.processor;

import org.apache.commons.lang.Validate;
import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.retriever.Retriever;
import org.jboss.lupic.suite.HorizontalAlignment;
import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.VerticalAlignment;

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

        Retriever retriever = getVisualSuite().getGlobalConfiguration().getMaskRetriever();
        Mask mask = new Mask(id, source, getProperties(), retriever, verticalAlignment, horizontalAlignment);
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
