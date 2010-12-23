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
package org.jboss.lupic.suite.utils;

import java.lang.reflect.Constructor;

import org.jboss.lupic.suite.Properties;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ConvertingProperties extends Properties {
    
    public ConvertingProperties() {
    }

    public ConvertingProperties(Properties properties) {
        this.include(properties);
    }

    private static final long serialVersionUID = 9212012944902693011L;

    public <T> T getProperty(String propertyKey, Class<T> tClass) {
        Object object = getProperty(propertyKey);

        if (object == null) {
            return null;
        }

        Constructor<T> constructor;
        try {
            constructor = tClass.getConstructor(String.class);
        } catch (Exception e) {
            throw new IllegalStateException("can't automatically convert property '" + propertyKey
                + "', the constructor " + tClass.getName() + "(String) can't be access", e);
        }

        try {
            return constructor.newInstance(object.toString());
        } catch (Exception e) {
            throw new IllegalStateException("can't automatically convert property, the call to " + tClass.getName()
                + "(\"" + propertyKey + "\") failed", e);
        }
    }

    public <T> T getProperty(String propertyKey, T defaultValue, Class<T> tClass) {
        T result = getProperty(propertyKey, tClass);

        if (result == null) {
            return defaultValue;
        }

        return result;
    }
}
