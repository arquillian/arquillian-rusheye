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
package org.jboss.lupic.suite;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jboss.lupic.retriever.sample.SampleRetriever;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Test extends Configuration {

    private String name;
    private Sample sample;
    private Set<Pattern> patterns = new LinkedHashSet<Pattern>();

    public Test(String name, SampleRetriever sampleRetriever) {
        this.name = name;
        this.sample = new Sample(name, sampleRetriever);
    }

    public String getName() {
        return name;
    }

    public Sample getSample() {
        return sample;
    }

    public Set<Pattern> getPatterns() {
        return patterns;
    }
}
