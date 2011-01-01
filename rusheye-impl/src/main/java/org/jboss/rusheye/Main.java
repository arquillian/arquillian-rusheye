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
package org.jboss.rusheye;

import java.io.File;
import java.io.IOException;

import org.jboss.rusheye.parser.Parser;
import org.jboss.rusheye.parser.listener.ParserListener;
import org.jboss.rusheye.suite.utils.Instantiator;
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
                ParserListener parserListener = new Instantiator<ParserListener>().getInstance(args[2]);
                parser.registerListener(parserListener);
            }
            parser.parseFile(visualSuiteDefinition);
        }
    }
}
