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

import java.io.IOException;
import org.arquillian.rusheye.exception.RetrieverException;
import org.arquillian.rusheye.retriever.Retriever;
import org.arquillian.rusheye.suite.Properties;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestMaskRetriever extends AbstractVisualSuiteDefinitionTest {

    private static final String SOURCE = "source";

    @Test
    public void testPropertiesShouldPass() throws SAXException, IOException {
        String retrieverImpl = TestPatternRetriever.AssertingRetriever.class.getName();
        stub.maskRetriever.addAttribute("type", retrieverImpl);

        stub.maskRetriever.addElement("xxx").setText("1");
        stub.maskRetriever.addElement("yyy").setText("2");

        startWriter();
        parse();

        Retriever retriever = handler.getVisualSuite().getGlobalConfiguration().getMaskRetriever();
        try {
            assertNull(retriever.retrieve(SOURCE, new Properties()));
        } catch (RetrieverException e) {
            fail();
        }
    }
}
