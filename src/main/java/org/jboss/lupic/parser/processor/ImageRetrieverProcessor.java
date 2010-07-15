package org.jboss.lupic.parser.processor;

import org.apache.commons.lang.Validate;
import org.jboss.lupic.parser.Processor;
import org.jboss.lupic.retriever.Retriever;

public class ImageRetrieverProcessor extends Processor {
	
	Retriever retriever;
	
	{
		setPropertiesEnabled(true);
	}
	
	@Override
	public void start() {
		String retrieverClassName = getAttribute("class");
		Validate
				.notNull(
						retrieverClassName,
						"image-retriever must have class attribute defined pointing to Retriever implementation");

		retriever = getRetrieverInstance(retrieverClassName);

		getVisualSuite().getGlobalConfiguration().setImageRetriever(retriever);
	}
	
	@Override
	public void end() {
		retriever.setDefaultProperties(getProperties());
	}

	private Retriever getRetrieverInstance(String retrieverClassName) {
		try {
			return getRetriverClass(retrieverClassName).newInstance();
		} catch (InstantiationException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private Class<? extends Retriever> getRetriverClass(
			String retrieverClassName) {
		try {
			return (Class<? extends Retriever>) Class
					.forName(retrieverClassName);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}
}
