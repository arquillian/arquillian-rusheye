package org.jboss.lupic.retriever;

import java.util.Properties;

public abstract class AbstractRetriever implements Retriever {

	private Properties properties = new Properties();

	@Override
	public void setDefaultProperties(Properties properties) {
		if (properties != null) {
			this.properties = properties;
		}
	}
	
	public Properties getProperties() {
		return properties;
	}
}
