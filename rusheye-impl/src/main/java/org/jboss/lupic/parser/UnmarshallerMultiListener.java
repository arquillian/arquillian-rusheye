package org.jboss.lupic.parser;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.Unmarshaller.Listener;

public class UnmarshallerMultiListener extends Listener {

    private Set<Listener> listeners = new LinkedHashSet<Listener>();

    @Override
    public void beforeUnmarshal(Object target, Object parent) {
        for (Listener listener : listeners) {
            listener.beforeUnmarshal(target, parent);
        }
    }

    @Override
    public void afterUnmarshal(Object target, Object parent) {
        for (Listener listener : listeners) {
            listener.afterUnmarshal(target, parent);
        }
    }

    public void registerListener(Listener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unregisterListener(Listener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

}
