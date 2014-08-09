/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.rusheye.arquillian.configuration;

import java.util.HashMap;
import java.util.Map;

import org.jboss.arquillian.core.spi.Validate;

/**
 * The base class for all configurations for all implemented extensions.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public abstract class Configuration<T extends Configuration<T>> {

    private Map<String, String> configuration = new HashMap<String, String>();

    /**
     * Gets configuration from Arquillian descriptor and creates instance of it.
     *
     * @param configuration configuration of extension from arquillian.xml
     * @return this
     * @throws IllegalArgumentException if {@code configuration} is a null object
     */
    public Configuration<T> setConfiguration(Map<String, String> configuration) {
        Validate.notNull(configuration, "Properties for configuration of recorder extension can not be a null object!");
        this.configuration = configuration;
        return this;
    }

    /**
     *
     * @return configuration of some extension
     */
    public Map<String, String> getConfiguration() {
        return this.configuration;
    }

    /**
     * Gets value of {@code name} property. In case a value for such name does not exist or is a null object or an empty string,
     * {@code defaultValue} is returned.
     *
     * @param name name of a property you want to get the value of
     * @param defaultValue value returned in case {@code name} is a null string or it is empty
     * @return value of a {@code name} property of {@code defaultValue} when {@code name} is null or empty string
     * @throws IllegalArgumentException if {@code name} is a null object or an empty string or if {@code defaultValue} is a null
     *         object
     */
    public String getProperty(String name, String defaultValue) throws IllegalStateException {
        Validate.notNullOrEmpty(name, "Unable to get the configuration value of null or empty configuration key");

        String found = getConfiguration().get(name);
        if (found == null || found.isEmpty()) {
            return defaultValue;
        } else {
            return found;
        }
    }

    /**
     * Sets some property.
     *
     * @param name acts as a key
     * @param value
     * @throws IllegalArgumentException if {@code name} is null or empty or {@code value} is null
     */
    public void setProperty(String name, String value) {
        Validate.notNullOrEmpty(name, "Name of property can not be a null object nor an empty string!");
        Validate.notNull(value, "Value of property can not be a null object!");

        configuration.put(name, value);
    }

    /**
     * Validates configuration.
     *
     * @throws RecorderConfigurationException when configuration of an extension is not valid
     */
    public abstract void validate() throws RusheyeConfigurationException;
}