package org.jboss.lupic;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.jboss.lupic.suite.Test;

public class TestUnmarshaller {

    Unmarshaller unmarshaller;

    public TestUnmarshaller() {
        JAXBContext jaxbContext;

        try {
            jaxbContext = JAXBContext.newInstance();
        } catch (JAXBException e) {
            throw new IllegalStateException("Failed to create instance of JAXBContext", e);
        }

        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new IllegalStateException("Failed to obtain Unmarshaller from JAXBContext", e);
        }
    }

    public Test unmarshall(InputStream inputStream) {
        try {
            JAXBElement<Test> element = (JAXBElement<Test>) unmarshaller.unmarshal(inputStream);
            return element.getValue();
        } catch (JAXBException e) {
            throw new IllegalStateException("Failed to marshall the Test", e);
        }
    }
}
