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
package org.jboss.lupic.parser.processor;

import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.suite.GlobalConfiguration;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class GlobalConfigurationProcessor extends Processor {

    {
        supportProcessor("listeners", ListenersProcessor.class);
        supportProcessor("image-retriever", RetrieverProcessor.class);
        supportProcessor("mask-retriever", RetrieverProcessor.class);
        supportProcessor("perception", PerceptionProcessor.class);
        supportProcessor("masks", MasksProcessor.class);
    }

    @Override
    public void start() {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();

        getVisualSuite().setGlobalConfiguration(globalConfiguration);
        getContext().setCurrentConfiguration(globalConfiguration);
    }

    @Override
    public void end() {
        getContext().invokeListeners().onConfigurationParsed(getVisualSuite());
    }
}
