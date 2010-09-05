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
package org.jboss.lupic.parser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import org.jboss.lupic.retriever.AbstractRetriever;
import org.jboss.lupic.retriever.MaskRetriever;
import org.jboss.lupic.retriever.PatternRetriever;
import org.jboss.lupic.retriever.Retriever;
import org.jboss.lupic.retriever.RetrieverException;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import static org.testng.Assert.*;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPatternRetriever extends AbstractVisualSuiteDefinitionTest {

	private static final String SOURCE = "source";

	@Test
	public void testPropertiesShouldPass() throws SAXException, IOException {
		String retrieverImpl = AssertingRetriever.class.getName();
		stub.patternRetriever.addAttribute("class", retrieverImpl);

		stub.patternRetriever.addElement("xxx").setText("1");
		stub.patternRetriever.addElement("yyy").setText("2");

		startWriter();
		parse();

		Retriever retriever = handler.getVisualSuite().getGlobalConfiguration()
				.getPatternRetriever();
		try {
			assertNull(retriever.retrieve(SOURCE, new Properties()));
		} catch (RetrieverException e) {
			fail();
		}

	}

	public static class AssertingRetriever extends AbstractRetriever implements PatternRetriever, MaskRetriever {
		@Override
		public BufferedImage retrieve(String source, Properties localProperties) {
			final Properties properties = mergeProperties(localProperties);

			assertSame(source, SOURCE);
			assertEquals(properties.get("xxx"), "1");
			assertEquals(properties.get("yyy"), "2");

			return null;
		}
	}
}
