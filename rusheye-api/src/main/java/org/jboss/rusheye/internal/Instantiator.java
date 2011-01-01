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
package org.jboss.rusheye.internal;

import org.jboss.rusheye.exception.ConfigurationException;

/**
 * Instantiates given type by class name.
 * 
 * @param <T>
 *            the type to instantiate
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Instantiator<T> {
    /**
     * Returns the instance of &lt;T&gt; assuming that className is name of the class of type &lt;T&gt;
     * 
     * @param className
     *            the name of the class
     * @return the instance of &lt;T&gt; assuming that className is name of the class of type &lt;T&gt;
     * 
     * @throws ConfigurationException
     *             when class with className cannot be found, cannot be instantiated due to it's nature or security
     *             purposes
     */
    public T getInstance(String className) {
        try {
            return getInstanceClass(className).newInstance();
        } catch (InstantiationException e) {
            throw new ConfigurationException(getMessage(className), e);

        } catch (IllegalAccessException e) {
            throw new ConfigurationException(getMessage(className), e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends T> getInstanceClass(String className) {
        try {
            return (Class<? extends T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException(getMessage(className), e);
        }
    }

    private String getMessage(String className) {
        return "Error when trying to create instance of class '" + className + "'";
    }
}
