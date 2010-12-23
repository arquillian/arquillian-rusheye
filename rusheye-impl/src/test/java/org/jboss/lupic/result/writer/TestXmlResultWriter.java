package org.jboss.lupic.result.writer;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.output.TeeOutputStream;
import org.jboss.lupic.PassingSAXErrorHandler;
import org.jboss.lupic.result.ResultConclusion;
import org.jboss.lupic.result.ResultDetail;
import org.jboss.lupic.suite.Area;
import org.jboss.lupic.suite.ComparisonResult;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Rectangle;
import org.jboss.lupic.suite.Test;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class TestXmlResultWriter {

	private static final String XML_VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
	private static final String XML_SCHEMA_FEATURE = "http://apache.org/xml/features/validation/schema";
	private static final String XML_SCHEMA_FULL_CHECKING_FEATURE = "http://apache.org/xml/features/validation/schema-full-checking";

	ValidationResultWriter writer = new ValidationResultWriter();

	CountDownLatch latch = new CountDownLatch(1);

	@org.testng.annotations.Test
	public void testXmlResultWriter() throws InterruptedException {
		ResultDetail detail = new ResultDetail();
		Test test = new Test();
		Pattern pattern = new Pattern();
		ComparisonResult comparisonResult = new ComparisonResult();
		Rectangle rectangle = new Rectangle();
		Area area = new Area();
		List<ResultDetail> detailList;

		test.setName("testName");
		test.getPatterns().add(pattern);
		pattern.setName("patternName");
		detail.setPattern(pattern);
		detail.setComparisonResult(comparisonResult);
		detail.setLocation("someLocation");
		detail.setConclusion(ResultConclusion.PERCEPTUALLY_SAME);
		comparisonResult.setEqualPixels(3);
		comparisonResult.setDifferentPixels(4);
		comparisonResult.setPerceptibleDiffs(5);
		comparisonResult.setSmallDifferences(6);
		comparisonResult.setTotalPixels(7);
		comparisonResult.getRectangles().add(rectangle);
		comparisonResult.setArea(area);
		area.setWidth(8);
		area.setHeight(9);
		rectangle.setMin(new Point(10, 11));
		rectangle.setMax(new Point(12, 13));
		detailList = Arrays.asList(new ResultDetail[] { detail });

		writer.write(test, detailList);
		writer.close();

		latch.await();
		Assert.assertNull(writer.validatingReader.validationMessage,
				writer.validatingReader.validationMessage);
	}

	@AfterMethod(alwaysRun = true)
	public void printDocumentOnFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			if (writer != null && writer.byteStream != null) {
				System.err.println(new String(writer.byteStream.toByteArray()));
			}
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
