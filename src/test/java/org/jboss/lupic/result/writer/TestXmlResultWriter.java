package org.jboss.lupic.result.writer;

import static org.mockito.Mockito.when;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.output.TeeOutputStream;
import org.jboss.lupic.PassingSAXErrorHandler;
import org.jboss.lupic.result.ResultConclusion;
import org.jboss.lupic.result.ResultDetail;
import org.jboss.lupic.suite.ComparisonResult;
import org.jboss.lupic.suite.Pattern;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class TestXmlResultWriter {

    private static final String XML_VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    private static final String XML_SCHEMA_FEATURE = "http://apache.org/xml/features/validation/schema";
    private static final String XML_SCHEMA_FULL_CHECKING_FEATURE = "http://apache.org/xml/features/validation/schema-full-checking";

    @Spy
    ValidationResultWriter writer = new ValidationResultWriter();

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

    CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void test() throws InterruptedException {
        Iterator<ResultDetail> detailIterator = new LinkedList<ResultDetail>(
            Arrays.asList(new ResultDetail[] { detail })).iterator();

        when(test.getName()).thenReturn("testName");
        when(pattern.getName()).thenReturn("patternName");
        when(detail.getPattern()).thenReturn(pattern);
        when(detail.getComparisonResult()).thenReturn(comparisonResult);
        when(detail.getConclusion()).thenReturn(ResultConclusion.PERCEPTUALLY_SAME);
        when(detail.getLocation()).thenReturn("someLocation");
        when(comparisonResult.getArea().getWidth()).thenReturn(1);
        when(comparisonResult.getArea().getHeight()).thenReturn(2);
        when(comparisonResult.getEqualPixels()).thenReturn(3);
        when(comparisonResult.getDifferentPixels()).thenReturn(4);
        when(comparisonResult.getPerceptibleDiffs()).thenReturn(5);
        when(comparisonResult.getSmallDifferences()).thenReturn(6);
        when(comparisonResult.getTotalPixels()).thenReturn(7);
        when(comparisonResult.getRectangle().getMin()).thenReturn(new Point(1, 2));
        when(comparisonResult.getRectangle().getMax()).thenReturn(new Point(3, 4));

        when(detailList.iterator()).thenReturn(detailIterator);

        writer.write(test, detailList);
        writer.close();

        latch.await();
        Assert.assertNull(writer.validatingReader.validationMessage, writer.validatingReader.validationMessage);
    }

    @AfterMethod(alwaysRun = true)
    public void printDocumentOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.err.println(new String(writer.byteStream.toByteArray()));
        }
    }

    public class ValidationResultWriter extends XmlResultWriter {

        PipedInputStream in;
        PipedOutputStream out;
        ValidatingReader validatingReader;
        TeeOutputStream tee;
        ByteArrayOutputStream byteStream;

        @Override
        protected OutputStream openOutputStream() throws Exception {
            in = new PipedInputStream();
            out = new PipedOutputStream(in);
            validatingReader = new ValidatingReader(in);
            byteStream = new ByteArrayOutputStream();
            tee = new TeeOutputStream(out, byteStream);

            new Thread(validatingReader).start();

            return tee;
        }

        @Override
        protected void closeOutputStream() throws Exception {
            out.close();
        }
    }

    public class ValidatingReader implements Runnable {
        private XMLReader reader;
        private InputStream in;
        private String validationMessage = null;

        public ValidatingReader(InputStream in) {
            this.in = in;
        }

        public void run() {
            try {
                reader = XMLReaderFactory.createXMLReader();
                reader.setFeature(XML_VALIDATION_FEATURE, true);
                reader.setFeature(XML_SCHEMA_FEATURE, true);
                reader.setFeature(XML_SCHEMA_FULL_CHECKING_FEATURE, true);
                reader.setContentHandler(new DefaultHandler());
                reader.setErrorHandler(new PassingSAXErrorHandler());
                reader.parse(new InputSource(in));
            } catch (Exception e) {
                validationMessage = e.getMessage();
                throw new IllegalStateException(e);
            } finally {
                latch.countDown();
            }
        }
    }
}
