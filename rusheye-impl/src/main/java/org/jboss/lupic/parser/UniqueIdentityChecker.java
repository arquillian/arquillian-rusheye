package org.jboss.lupic.parser;

import javax.xml.bind.Unmarshaller;

import org.jboss.lupic.exception.ConfigurationValidationException;
import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Test;

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
