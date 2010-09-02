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
import org.jboss.lupic.parser.ParserListener;
import org.jboss.lupic.parser.Processor;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ListenerProcessor extends Processor {

    ParserListener parserListener;

    {
        setPropertiesEnabled(true);
    }

    @Override
    public void start() {
        String listenerClassName = getAttribute("class");
        Validate.notNull(listenerClassName,
            "listener must have class attribute defined pointing to Retriever implementation");

        parserListener = getParserListenerInstance(listenerClassName);
        getVisualSuite().getGlobalConfiguration().getConfiguredListeners().add(parserListener);
    }

    @Override
    public void end() {
        parserListener.setProperties(getProperties());
    }

    public static ParserListener getParserListenerInstance(String listenerClassName) {
        try {
            return getParserListenerClass(listenerClassName).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends ParserListener> getParserListenerClass(String retrieverClassName) {
        try {
            return (Class<? extends ParserListener>) Class.forName(retrieverClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("The configured ParserListener class was not found", e);
        }
    }
}
