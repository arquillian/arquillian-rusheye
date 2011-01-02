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

import java.io.IOException;

import org.xml.sax.SAXException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) throws SAXException, IOException {
        Commander cm = new Commander();

        try {
            cm.parse(args);
        } catch (ParameterException e) {
            printUsageMessage(cm, e);
            System.exit(1);
        }

        CommandBase base = cm.getCommand();
        JCommander jc = cm.getJCommander();

        if (base instanceof CommandMain) {
            jc.usage();
        } else {
            if (base.isHelp()) {
                jc.usage();
                System.exit(0);
            }

            try {
                base.validate();
            } catch (CommandValidationException e) {
                printUsageMessage(cm, e);
                System.exit(2);
            }

            if (base instanceof CommandCompare) {
                CommandCompare command = (CommandCompare) base;
                command.compare();
                command.printResult();
                
                if (command.isOutputSet()) {
                    command.writeDifferenceImage();
                }
            }
            
            if (base instanceof CommandCrawl) {
                CommandCrawl command = (CommandCrawl) base;
                command.crawl();
            }
        }
    }

    private static void printUsageMessage(Commander cm, Exception e) {
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append(e.getMessage() + "\n\n");
        cm.getJCommander().usage(errorMsg);
        System.err.println(errorMsg);
    }
}
