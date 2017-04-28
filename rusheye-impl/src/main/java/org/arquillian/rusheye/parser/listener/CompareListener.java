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
package org.arquillian.rusheye.parser.listener;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

import org.arquillian.rusheye.comparison.ImageComparator;
import org.arquillian.rusheye.core.DefaultImageComparator;
import org.arquillian.rusheye.internal.Instantiator;
import org.arquillian.rusheye.listener.SuiteListener;
import org.arquillian.rusheye.result.ResultCollector;
import org.arquillian.rusheye.suite.ComparisonResult;
import org.arquillian.rusheye.suite.Configuration;
import org.arquillian.rusheye.suite.Mask;
import org.arquillian.rusheye.suite.Pattern;
import org.arquillian.rusheye.suite.Properties;
import org.arquillian.rusheye.suite.Sample;
import org.arquillian.rusheye.suite.Test;
import org.arquillian.rusheye.suite.VisualSuite;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class CompareListener implements SuiteListener {
    Properties properties = new Properties();
    ImageComparator imageComparator = new DefaultImageComparator();
    VisualSuite visualSuite;
    ResultCollector resultCollector;

    @Override
    public void setProperties(Properties properties) {
        this.properties.include(properties);
    }

    @Override
    public void onSuiteStarted(VisualSuite visualSuite) {
        this.visualSuite = visualSuite;
        String resultListenerClass = (String) properties.getProperty("result-collector");
        resultCollector = new Instantiator<ResultCollector>().getInstance(resultListenerClass);
        resultCollector.setProperties(properties);
        resultCollector.onSuiteStarted(visualSuite);
    }

    @Override
    public void onConfigurationReady(VisualSuite visualSuite) {
        resultCollector.onConfigurationReady(visualSuite);
    }

    @Override
    public void onSuiteReady(VisualSuite visualSuite) {
        resultCollector.onSuiteReady(visualSuite);
        resultCollector.onSuiteCompleted(visualSuite);
    }

    @Override
    public void onPatternReady(Configuration configuration, Pattern pattern) {
        pattern.include(properties);
        pattern.run();
        resultCollector.onPatternReady(configuration, pattern);
        resultCollector.onPatternStarted(pattern);
    }

    @Override
    public void onTestReady(Test test) {
        resultCollector.onTestReady(test);
        resultCollector.onTestStarted(test);

        resultCollector.onSampleStarted(test);
        
        Sample sample = test.getSample();
        sample.include(properties);
        BufferedImage sampleImage = getSampleImage(sample);
        
        for (Mask mask : test.getMasks()) {
            mask.include(properties);
        }
        
        resultCollector.onSampleLoaded(test);

        for (Pattern pattern : test.getPatterns()) {
            BufferedImage patternImage = getPatternImage(pattern);
            resultCollector.onPatternLoaded(test, pattern);

            ComparisonResult comparisonResult = imageComparator.compare(patternImage, sampleImage,
                test.getPerception(), test.getSelectiveAlphaMasks());
            resultCollector.onPatternCompleted(test, pattern, comparisonResult);
        }
        resultCollector.onTestCompleted(test);
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