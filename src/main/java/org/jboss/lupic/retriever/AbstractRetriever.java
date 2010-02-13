package org.jboss.lupic.retriever;

import java.util.Properties;

public abstract class AbstractRetriever implements Retriever {

	private Properties properties;

	protected Properties getProperties() {
		return this.properties;
	}
	
	public Properties setProperties() {
		return this.properties;
	}
}
