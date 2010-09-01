/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
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
package org.jboss.lupic.parser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

import org.jboss.lupic.parser.processor.VisualSuiteProcessor;
import org.jboss.lupic.suite.VisualSuite;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Handler extends DefaultHandler {

    private Context context;
    private VisualSuite visualSuite;
    private Deque<Processor> processors = new LinkedList<Processor>();
    private Set<ParserListener> parserListeners;

    private String characters = null;

    public Handler(Set<ParserListener> parserListeners) {
        this.parserListeners = parserListeners;
    }

    @Override
    public void startDocument() throws SAXException {
        context = new ListeningContext();
        visualSuite = new VisualSuite();
        context.invokeListeners().suiteStarted(visualSuite);
    }

    @Override
    public void endDocument() throws SAXException {
        context.invokeListeners().suiteCompleted(visualSuite);
    }

    @Override
    public void startElement(String uri, String tagName, String qName, Attributes attributes) throws SAXException {

        Processor processor;

        if (processors.isEmpty()) {
            if (tagName.equals("visual-suite")) {
                processor = new VisualSuiteProcessor();
            } else {
                throw new IllegalStateException();
            }
        } else {
            processor = processors.getFirst().getProcessor(tagName);
        }

        processors.push(processor);

        processor.setContext(context);
        processor.setVisualSuite(visualSuite);
        processor.setAttributes(attributes);
        processor.start();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        characters = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        processors.getFirst().process(characters);
        characters = null;

        Processor currentProcessor = processors.getFirst();
        currentProcessor.end();
        processors.removeFirst();
    }

    private class ListeningContext extends Context implements InvocationHandler {

        ParserListener wrappedListener = (ParserListener) Proxy.newProxyInstance(Handler.this.getClass()
            .getClassLoader(), new Class<?>[] { ParserListener.class }, this);

        @Override
        public ParserListener invokeListeners() {
            return wrappedListener;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (ParserListener listener : parserListeners) {
                Method wrappedMethod = listener.getClass().getMethod(method.getName(), method.getParameterTypes());
                try {
                    wrappedMethod.invoke(listener, args);
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof RuntimeException) {
                        throw (RuntimeException) e.getCause();
                    } else {
                        throw new RuntimeException(e.getCause());
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("unexpected invocation exception: " + e.getMessage(), e);
                }
            }

            return null;
        }
    }

    public VisualSuite getVisualSuite() {
        return visualSuite;
    }

    Context getContext() {
        return context;
    }
}
