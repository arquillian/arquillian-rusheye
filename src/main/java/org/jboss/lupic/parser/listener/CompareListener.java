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
package org.jboss.lupic.parser.listener;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

import org.jboss.lupic.core.ComparisonResult;
import org.jboss.lupic.core.ImageComparator;
import org.jboss.lupic.parser.ParserListenerAdapter;
import org.jboss.lupic.retriever.RetrieverException;
import org.jboss.lupic.retriever.sample.SampleRetriever;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Sample;
import org.jboss.lupic.suite.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class CompareListener extends ParserListenerAdapter {
    ImageComparator imageComparator = new ImageComparator();

    @Override
    public void onPatternParsed(Configuration configuration, Pattern pattern) {
        pattern.run();
    }

    @Override
    public void onTestParsed(Test test) {
        BufferedImage sampleImage = getSampleImage(test.getSample());

        for (Pattern pattern : test.getPatterns()) {
            BufferedImage patternImage = getPatternImage(pattern);

            ComparisonResult comparisonResult = imageComparator.compare(patternImage, sampleImage,
                test.getPerception(), test.getSelectiveAlphaMasks());
        }
    }

    private BufferedImage getSampleImage(Sample sample) {
        sample.run();
        try {
            return sample.get();
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    private BufferedImage getPatternImage(Pattern pattern) {
        try {
            return pattern.get();
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}