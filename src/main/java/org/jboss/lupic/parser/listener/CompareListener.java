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
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.jboss.lupic.core.ComparisonResult;
import org.jboss.lupic.core.ImageComparator;
import org.jboss.lupic.result.ResultCollector;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Sample;
import org.jboss.lupic.suite.Test;
import org.jboss.lupic.suite.VisualSuite;
import org.jboss.lupic.suite.utils.Instantiator;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class CompareListener implements ParserListener {
    Properties properties;
    ImageComparator imageComparator = new ImageComparator();
    VisualSuite visualSuite;
    ResultCollector resultListener;
    boolean suiteParsed = false;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void onSuiteStarted(VisualSuite visualSuite) {
        this.visualSuite = visualSuite;
        String resultListenerClass = properties.getProperty("result-listener");
        resultListener = new Instantiator<ResultCollector>().getInstance(resultListenerClass);
        resultListener.setProperties(properties);
        resultListener.onSuiteStarted(visualSuite);
    }

    @Override
    public void onConfigurationParsed(VisualSuite visualSuite) {
        resultListener.onConfigurationParsed(visualSuite);
    }

    @Override
    public void onSuiteParsed(VisualSuite visualSuite) {
        resultListener.onSuiteParsed(visualSuite);
        suiteParsed = true;
    }

    @Override
    public void onPatternParsed(Configuration configuration, Pattern pattern) {
        onPatternParsed(configuration, pattern);
        pattern.run();
        resultListener.onPatternStarted(pattern);
    }

    @Override
    public void onTestParsed(Test test) {
        resultListener.onTestStarted(test);

        resultListener.onSampleStarted(test);
        BufferedImage sampleImage = getSampleImage(test.getSample());
        resultListener.onSampleLoaded(test);

        for (Pattern pattern : test.getPatterns()) {
            BufferedImage patternImage = getPatternImage(pattern);
            resultListener.onPatternLoaded(test, pattern);

            ComparisonResult comparisonResult = imageComparator.compare(patternImage, sampleImage,
                test.getPerception(), test.getSelectiveAlphaMasks());
            resultListener.onPatternCompleted(test, pattern, comparisonResult);
        }
        resultListener.onTestCompleted(test);

        if (suiteParsed) {
            resultListener.onSuiteCompleted(visualSuite);
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