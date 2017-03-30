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
package org.jboss.rusheye.parser;

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
