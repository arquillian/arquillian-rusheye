package org.jboss.lupic.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jboss.lupic.parser.processor.PropertiesProcessor;
import org.jboss.lupic.suite.VisualSuite;
import org.xml.sax.Attributes;

public abstract class Processor {

	private Map<String, Class<? extends Processor>> processorMap = new HashMap<String, Class<? extends Processor>>();
	private boolean propertiesEnabled = false;

	private Context context;
	private VisualSuite visualSuite;
	private Attributes attributes;
	private Properties properties;

	public void start() {
	}

	public void process(String content) {
	}

	public void end() {
	}

	protected String getAttribute(String localName) {
		return attributes.getValue("", localName);
	}

	protected Properties getProperties() {
		return properties;
	}
	
	void setContext(Context context) {
		this.context = context;
	}
	
	protected Context getContext() {
		return context;
	}

	void setVisualSuite(VisualSuite visualSuite) {
		this.visualSuite = visualSuite;
	}

	protected VisualSuite getVisualSuite() {
		return visualSuite;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	protected void supportProcessor(String tagName,
			Class<? extends Processor> processorClass) {
		this.processorMap.put(tagName, processorClass);
	}

	protected void setPropertiesEnabled(boolean propertiesEnabled) {
		this.propertiesEnabled = propertiesEnabled;
	}

	public Processor getProcessor(String tagName) {
		Class<? extends Processor> processorClass = processorMap.get(tagName);

		if (processorClass == null && propertiesEnabled) {
			if (properties == null) {
				properties = new Properties();
			}
			return new PropertiesProcessor(properties, tagName);
		}

		if (processorClass == null) {
			throw new IllegalStateException(
					"The target processor for tag name '" + tagName
							+ "' doesn't exists");
		}

		try {
			return processorClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalStateException();
		} catch (IllegalAccessException e) {
			throw new IllegalStateException();
		}
	}
}
