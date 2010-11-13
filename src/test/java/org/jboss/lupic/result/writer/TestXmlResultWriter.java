package org.jboss.lupic.result.writer;

import static org.mockito.Mockito.when;

import java.awt.Point;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jboss.lupic.core.ComparisonResult;
import org.jboss.lupic.result.ResultConclusion;
import org.jboss.lupic.result.ResultDetail;
import org.jboss.lupic.suite.Pattern;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestXmlResultWriter {

    @Spy
    XmlResultWriter writer = new StdOutXmlResultWriter();

    @Mock
    List<ResultDetail> detailList;

    @Mock
    ResultDetail detail;

    @Mock
    org.jboss.lupic.suite.Test test;

    @Mock
    Pattern pattern;

    @Mock
    ComparisonResult comparisonResult;

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        Iterator<ResultDetail> detailIterator = new LinkedList<ResultDetail>(
            Arrays.asList(new ResultDetail[] { detail })).iterator();

        when(test.getName()).thenReturn("testName");
        when(pattern.getName()).thenReturn("patternName");
        when(detail.getPattern()).thenReturn(pattern);
        when(detail.getComparisonResult()).thenReturn(comparisonResult);
        when(detail.getConclusion()).thenReturn(ResultConclusion.PERCEPTUALLY_SAME);
        when(detail.getLocation()).thenReturn("someLocation");
        when(comparisonResult.getAreaHeight()).thenReturn(1);
        when(comparisonResult.getAreaWidth()).thenReturn(2);
        when(comparisonResult.getEqualPixels()).thenReturn(3);
        when(comparisonResult.getDifferentPixels()).thenReturn(4);
        when(comparisonResult.getPerceptibleDiffs()).thenReturn(5);
        when(comparisonResult.getSmallDifferences()).thenReturn(6);
        when(comparisonResult.getTotalPixels()).thenReturn(7);
        when(comparisonResult.getRectangleMin()).thenReturn(new Point(1, 2));
        when(comparisonResult.getRectangleMax()).thenReturn(new Point(3, 4));

        when(detailList.iterator()).thenReturn(detailIterator);

        writer.write(test, detailList);
        writer.close();
    }

    public static class StdOutXmlResultWriter extends XmlResultWriter {
        @Override
        protected OutputStream openOutputStream() throws Exception {
            return System.out;
        }

        @Override
        protected void closeOutputStream() throws Exception {
            System.out.println();
        }
    }
}
