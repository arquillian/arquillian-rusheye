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
package org.arquillian.rusheye.parser;

import org.dom4j.Element;

import static org.arquillian.rusheye.parser.VisualSuiteDefinitions.PATTERN;
import static org.arquillian.rusheye.parser.VisualSuiteDefinitions.TEST;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class AbstractTestOfTestElement extends AbstractVisualSuiteDefinitionTest {
    protected static final String TEST1_NAME = "test1-name";
    protected static final String PATTERN1_NAME = "pattern1-name";

    protected static final String TEST2_NAME = "test2-name";
    protected static final String PATTERN2_NAME = "pattern2-name";

    protected static final String PATTERN3_NAME = "pattern3-name";

    protected Element test;
    protected Element pattern;

    protected void addTest(String name) {
        test = stub.visualSuite.addElement(TEST);
        if (name != null) {
            test.addAttribute("name", name);
        }
    }

    protected void addPattern(String name) {
        pattern = test.addElement(PATTERN);
        if (name != null) {
            pattern.addAttribute("name", name);
        }
    }
}
