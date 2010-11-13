package org.jboss.lupic.result.writer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;

public class PrettyXMLStreamWriter implements InvocationHandler {

    int indentation = 0;
    boolean simpleContent;
    XMLStreamWriter writer;

    public PrettyXMLStreamWriter(XMLStreamWriter writer) {
        this.writer = writer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;

        if ("writeStartElement".equals(method.getName())) {
            writer.writeCharacters("\n");
            writer.writeCharacters(StringUtils.repeat("\t", indentation));
            indentation++;
        } else if ("writeEndElement".equals(method.getName())) {
            indentation--;
            if (!simpleContent) {
                writer.writeCharacters("\n");
                writer.writeCharacters(StringUtils.repeat("\t", indentation));
            }
        } else if ("writeEmptyElement".equals(method.getName())) {
            writer.writeCharacters("\n");
            writer.writeCharacters(StringUtils.repeat("\t", indentation));
        }

        if ("writeStartElement".equals(method.getName())) {
            simpleContent = true;
        } else if ("writeCharacters".equals(method.getName())) {
        } else {
            simpleContent = false;
        }

        result = method.invoke(writer, args);

        return result;
    }

    public static XMLStreamWriter pretty(XMLStreamWriter writer) {
        return (XMLStreamWriter) Proxy.newProxyInstance(writer.getClass().getClassLoader(),
            new Class[] { XMLStreamWriter.class }, new PrettyXMLStreamWriter(writer));
    }

}
