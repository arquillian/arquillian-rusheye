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

import com.beust.jcommander.JCommander;

public class Commander {

    JCommander jCommander;

    CommandBase main = new CommandBase();
    CommandCompare compare = new CommandCompare();
    CommandCrawl crawl = new CommandCrawl();
    CommandParse parse = new CommandParse();

    public Commander() {
        this.jCommander = new JCommander(main);
        jCommander.addCommand("compare", compare);
        jCommander.addCommand("crawl", crawl);
        jCommander.addCommand("parse", parse);
    }
    
    public void parse(String[] args) {
        jCommander.parse(args);
    }

    public CommandParse getParse() {
        return parse;
    }

    public JCommander getJCommander() {
        String commandName = jCommander.getParsedCommand();
        if (commandName == null) {
            return jCommander;
        }
        return jCommander.getCommands().get(commandName);
    }

    public <T extends CommandBase> T getCommand() {
        String commandName = jCommander.getParsedCommand();
        if (commandName == null) {
            return (T) main;
        }
        return (T) jCommander.getCommands().get(commandName).getObjects().get(0);
    }
}
