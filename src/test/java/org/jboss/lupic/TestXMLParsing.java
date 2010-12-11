package org.jboss.lupic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CountDownLatch;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.jboss.lupic.retriever.sample.SampleRetrieverAdapter;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.Test;
import org.jboss.lupic.suite.utils.NullingProxy;
import org.jboss.lupic.suite.utils.VisualSuiteResult;
import org.testng.annotations.BeforeMethod;

public class TestXMLParsing {

    // @SuppressWarnings("unchecked")
    // @org.testng.annotations.Test
    // public void testMarshalling() throws JAXBException, InstantiationException, IllegalAccessException {
    // JAXBContext jaxbContext = JAXBContext.newInstance(Test.class);
    //
    // Marshaller marshaller = jaxbContext.createMarshaller();
    // marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    //
    // Test test = new Test("muj-test", new SampleRetrieverAdapter());
    //
    // Pattern pattern1 = new Pattern();
    // pattern1.setName("muj-vzor1");
    // pattern1.setSource("muj-vzor1-source");
    // pattern1.getProperties().setProperty("testovaci", "blabla");
    //
    // Pattern pattern2 = new Pattern();
    // pattern2.setName("muj-vzor2");
    // pattern2.setSource("muj-vzor2-source");
    //
    // test.getPatterns().add(pattern1);
    // test.getPatterns().add(pattern2);
    //
    // test = NullingProxy.handle(test, VisualSuiteResult.class);
    //
    // JAXBElement<Test> aElement = new JAXBElement<Test>(new QName("", "test"), Test.class, test);
    //
    // marshaller.marshal(aElement, System.out);
    // }

    OutputStream outputStream;

    @BeforeMethod
    public void setupInitialStream() {
        outputStream = System.out;
    }

    // @org.testng.annotations.Test
    public void testMarshalling1() {
        TestMarshaller marshaller = new TestMarshaller(VisualSuiteResult.class);

        Test test = new Test("muj-test", new SampleRetrieverAdapter());

        Pattern pattern1 = new Pattern();
        pattern1.setName("muj-vzor1");
        pattern1.setSource("muj-vzor1-source");
        pattern1.getProperties().setProperty("testovaci", "blabla");

        Pattern pattern2 = new Pattern();
        pattern2.setName("muj-vzor2");
        pattern2.setSource("muj-vzor2-source");

        test.getPatterns().add(pattern1);
        test.getPatterns().add(pattern2);

        marshaller.marshall(test, outputStream);
    }

    CountDownLatch latch = new CountDownLatch(1);

    @org.testng.annotations.Test
    public void testUnmarshalling() throws InterruptedException {
        Unmarshalling unmarshalling = new Unmarshalling();
        new Thread(unmarshalling).start();
        outputStream = unmarshalling.getOutputStream();
        testMarshalling1();
        latch.await();
    }

    private class Unmarshalling implements Runnable {

        PipedInputStream in;
        PipedOutputStream out;

        public Unmarshalling() {
            try {
                in = new PipedInputStream();
                out = new PipedOutputStream(in);
            } catch (IOException e) {
                throw new IllegalStateException();
            }
        }

        public OutputStream getOutputStream() {
            return out;
        }

        @Override
        public void run() {
            try {
                TestUnmarshaller unmarshaller = new TestUnmarshaller();
                Test test = unmarshaller.unmarshall(in);
                System.err.println(test);
            } finally {
                latch.countDown();
            }
        }
    }
}
