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
package org.arquillian.rusheye.result.writer;

import java.util.Iterator;

import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class WriterContext {
    private Test test;
    private Iterator<Pattern> patternIterator;
    private Pattern currentPattern;

    public WriterContext(Test test) {
        this.test = test;
        this.patternIterator = test.getPatterns().iterator();
    }

    public Test getTest() {
        return test;
    }

    public boolean hasNextDetail() {
        return patternIterator.hasNext();
    }

    public Pattern getNextDetail() {
        currentPattern = patternIterator.next();
        return currentPattern;
    }

    public Pattern getCurrentDetail() {
        return currentPattern;
    }
}
