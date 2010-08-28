package org.jboss.lupic.suite;

import java.util.LinkedHashSet;
import java.util.Set;

public class Test extends Configuration {
    Set<Pattern> patterns = new LinkedHashSet<Pattern>();

    public Set<Pattern> getPatterns() {
        return patterns;
    }
}
