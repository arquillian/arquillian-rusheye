package org.jboss.lupic.retriever;

import java.util.Properties;

public abstract class AbstractRetriever implements Retriever {

	private Properties properties = new Properties();

	@Override
	public void setGlobalProperties(Properties properties) {
		if (properties != null) {
			this.properties = properties;
		}
	}
	
	public Properties mergeProperties(Properties localProperties) {
		Properties result = new Properties();
		result.putAll(properties);
		result.putAll(localProperties);
		return result;
	}
}
