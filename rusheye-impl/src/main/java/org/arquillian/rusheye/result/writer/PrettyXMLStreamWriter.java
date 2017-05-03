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
package org.arquillian.rusheye.result.writer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.arquillian.rusheye.RushEye;

public class PrettyXMLStreamWriter implements InvocationHandler {

    int indentation = 0;
    boolean simpleContent;
    XMLStreamWriter writer;
    List<WriteAttribute> writeAttributes = new LinkedList<WriteAttribute>();

    public PrettyXMLStreamWriter(XMLStreamWriter writer) {
        this.writer = writer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result;

        // namespace handling
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (RushEye.NAMESPACE_VISUAL_SUITE.equals(args[i])) {
                    args[i] = RushEye.NAMESPACE_VISUAL_SUITE_RESULT;
                }
            }
        }
        if ("writeNamespace".equals(method.getName()) || "writeDefaultNamespace".equals(method.getName())) {
            if (indentation > 1) {
                return null;
            }
        }

        // attribute reordering (alphabethical order)
        if ("writeAttribute".equals(method.getName())) {
            writeAttributes.add(new WriteAttribute(method, args));
            return null;
        } else if (!writeAttributes.isEmpty()) {
            Collections.sort(writeAttributes);
            for (WriteAttribute writeAttribute : writeAttributes) {
                invoke(writeAttribute.method, writeAttribute.args);
            }
            writeAttributes.clear();
        }

        // handling of empty elements
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
        // decision about simple content (incl. empty elements)
        if ("writeStartElement".equals(method.getName())) {
            simpleContent = true;
        } else if ("writeCharacters".equals(method.getName()) || "writeAttribute".equals(method.getName())) {
            // intentionally left blank
        } else {
            simpleContent = false;
        }

        result = invoke(method, args);

        return result;
    }

    private Object invoke(Method method, Object[] args) throws IllegalArgumentException, IllegalAccessException,
        InvocationTargetException {
        return method.invoke(writer, args);
    }

    public static XMLStreamWriter pretty(XMLStreamWriter writer) {
        return (XMLStreamWriter) Proxy.newProxyInstance(writer.getClass().getClassLoader(),
            new Class[] { XMLStreamWriter.class }, new PrettyXMLStreamWriter(writer));
    }

    private class WriteAttribute implements Comparable<WriteAttribute> {
        Method method;
        Object[] args;
        String attributeName;

        public WriteAttribute(Method method, Object[] args) {
            this.method = method;
            this.args = args;
            attributeName = (String) args[args.length - 2];
        }

        @Override
        public int compareTo(WriteAttribute o) {
            return attributeName.compareTo(o.attributeName);
        }
    }
}
