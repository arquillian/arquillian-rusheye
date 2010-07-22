package org.jboss.lupic.suite;

import org.jboss.lupic.retriever.Retriever;

public class GlobalConfiguration extends Configuration {
	Retriever imageRetriever;
	

	public Retriever getImageRetriever() {
		return imageRetriever;
	}

	public void setImageRetriever(Retriever imageRetriever) {
		this.imageRetriever = imageRetriever;
	}

	
}
