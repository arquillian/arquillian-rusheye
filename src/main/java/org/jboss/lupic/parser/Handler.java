package org.jboss.lupic.parser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jboss.lupic.parser.processor.VisualSuiteProcessor;
import org.jboss.lupic.suite.VisualSuite;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler {

	private Context context;
	private VisualSuite visualSuite;
	private Deque<Processor> processors = new LinkedList<Processor>();
	private Set<ParserListener> listeners = new HashSet<ParserListener>();
	private String characters = null;

	@Override
	public void startDocument() throws SAXException {
		context = new ListeningContext();
		visualSuite = new VisualSuite();
		context.getListener().suiteStarted(visualSuite);
	}

	@Override
	public void endDocument() throws SAXException {
		context.getListener().suiteCompleted(visualSuite);
	}

	@Override
	public void startElement(String uri, String tagName, String qName,
			Attributes attributes) throws SAXException {

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
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		characters = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		processors.getFirst().process(characters);
		characters = null;

		Processor currentProcessor = processors.getFirst();
		currentProcessor.end();
		processors.removeFirst();
	}

	public void registerListener(ParserListener parserListener) {
		synchronized (parserListener) {
			if (listeners.contains(parserListener)) {
				return;
			}
			listeners.add(parserListener);
		}
	}

	public void unregisterListener(ParserListener parserListener) {
		synchronized (listeners) {
			if (listeners.contains(parserListener)) {
				listeners.remove(parserListener);
			}
		}
		throw new IllegalStateException("Given parser isn't registered");
	}

	private class ListeningContext extends Context implements InvocationHandler {

		ParserListener wrappedListener = (ParserListener) Proxy
				.newProxyInstance(Handler.this.getClass().getClassLoader(),
						new Class<?>[] { ParserListener.class }, this);

		@Override
		public ParserListener getListener() {
			return wrappedListener;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			for (ParserListener listener : listeners) {
				Method wrappedMethod = listener.getClass().getMethod(
						method.getName(), method.getParameterTypes());
				try {
					wrappedMethod.invoke(listener, args);
				} catch (InvocationTargetException e) {
					if (e.getCause() instanceof RuntimeException) {
						throw (RuntimeException) e.getCause();
					} else {
						throw new RuntimeException(e.getCause());
					}
				} catch (Exception e) {
					throw new IllegalStateException(
							"unexpected invocation exception: "
									+ e.getMessage(), e);
				}
			}

			return null;
		}
	}
	
	public VisualSuite getVisualSuite() {
		return visualSuite;
	}
}
