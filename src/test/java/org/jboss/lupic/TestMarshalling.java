package org.jboss.lupic;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.jboss.lupic.suite.utils.VisualSuiteResult;
import org.jboss.lupic.suite.utils.NullingProxy;
import org.testng.annotations.Test;

public class TestMarshalling {
    @Test
    public void test() throws JAXBException, InstantiationException, IllegalAccessException {
        JAXBContext jaxbContext = JAXBContext.newInstance(A.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        A a = new A();
        a.setB(new B());
        a.setC(new C());

        A aMarshall = a.marshallResult();

        JAXBElement<A> aElement = new JAXBElement<A>(new QName("", "a"), A.class, aMarshall);

        marshaller.marshal(aElement, System.out);
    }

    public static class A {
        private B b;
        private C c;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }

        @VisualSuiteResult
        public C getC() {
            return c;
        }

        public void setC(C c) {
            this.c = c;
        }

        @SuppressWarnings("unchecked")
        public A marshallResult() throws InstantiationException, IllegalAccessException {
            return NullingProxy.handle(this, VisualSuiteResult.class);
        }
    }

    public static class B {
    }

    public static class C {
    }
}
