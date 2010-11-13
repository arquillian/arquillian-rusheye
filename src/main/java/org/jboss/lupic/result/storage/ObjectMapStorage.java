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
package org.jboss.lupic.result.storage;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ObjectMapStorage implements ResultStorage {

    HashMap<String, BufferedImage> map = new HashMap<String, BufferedImage>();

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public String store(Test test, Pattern pattern, BufferedImage differenceImage) {
        final String key = test.getName() + "-" + pattern.getName();
        map.put(key, differenceImage);
        return key;
    }

    @Override
    public void end() {
    }

    public Map<String, BufferedImage> getMap() {
        return Collections.unmodifiableMap(map);
    }

}
