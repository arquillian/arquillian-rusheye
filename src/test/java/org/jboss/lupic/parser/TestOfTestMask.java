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
