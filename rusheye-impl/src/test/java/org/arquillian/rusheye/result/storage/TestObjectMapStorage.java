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
package org.arquillian.rusheye.result.storage;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.arquillian.rusheye.suite.Pattern;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestObjectMapStorage {

    ObjectMapStorage storage = new ObjectMapStorage();

    @Mock
    org.arquillian.rusheye.suite.Test test;

    @Mock
    Pattern pattern;

    @Mock
    BufferedImage bufferedImage;

    @BeforeTest
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testObjectMapStorage() throws IOException {
        when(test.getName()).thenReturn("test");
        when(pattern.getName()).thenReturn("pattern");

        String key = storage.store(test, pattern, bufferedImage);

        assertSame(storage.getMap().get(key), bufferedImage);
    }

}
