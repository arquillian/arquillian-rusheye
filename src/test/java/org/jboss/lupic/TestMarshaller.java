package org.jboss.lupic;

import java.io.OutputStream;
import java.lang.annotation.Annotation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.jboss.lupic.suite.Test;
import org.jboss.lupic.suite.utils.NullingProxy;

public class TestMarshaller {

    Marshaller marshaller;
    Class<? extends Annotation> nullified;

    public TestMarshaller(Class<? extends Annotation> nullified) {
        JAXBContext jaxbContext;
        this.nullified = nullified;

        try {
            jaxbContext = JAXBContext.newInstance(Test.class);
        } catch (JAXBException e) {
            throw new IllegalStateException("Failed to create instance of JAXBContext", e);
        }

        try {
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            throw new IllegalStateException("Failed to obtain Marshaller from JAXBContext", e);
        }

        try {
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            throw new IllegalStateException("Failed to set properties to Marshaller", e);
        }
    }

    public void marshall(Test test, OutputStream outputStream) {
        Test resultSchemaAgnosticTest;
        try {
            resultSchemaAgnosticTest = NullingProxy.handle(test, nullified);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to wrap the Test class with NullingProxy", e);
        }

        JAXBElement<Test> testElement = new JAXBElement<Test>(new QName("", "test"), Test.class,
            resultSchemaAgnosticTest);

        try {
            marshaller.marshal(testElement, outputStream);
        } catch (JAXBException e) {
            throw new IllegalStateException("Failed to marshall the Test", e);
        }
    }
}
