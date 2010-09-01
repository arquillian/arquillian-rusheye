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
package org.jboss.lupic;

import java.io.File;
import java.io.IOException;

import org.jboss.lupic.parser.Parser;
import org.jboss.lupic.parser.ParserListener;
import org.jboss.lupic.parser.processor.ListenerProcessor;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) throws SAXException, IOException {
        final String cmd = args[0];

        if (cmd.equals("parse")) {
            File visualSuiteDefinition = new File(args[1]);
            System.setProperty("user.dir", visualSuiteDefinition.getParent());
            Parser parser = new Parser();
            if (args.length > 2) {
                ParserListener parserListener = ListenerProcessor.getParserListenerInstance(args[2]);
                parser.registerListener(parserListener);
            }
            parser.parseFile(visualSuiteDefinition);
        }
    }
}
