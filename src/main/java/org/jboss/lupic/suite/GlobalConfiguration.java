package org.jboss.lupic.suite;

import org.jboss.lupic.retriever.Retriever;

public class GlobalConfiguration extends Configuration {
	Retriever imageRetriever;
	Retriever maskRetriever;

	public Retriever getImageRetriever() {
		return imageRetriever;
	}

	public void setImageRetriever(Retriever imageRetriever) {
		this.imageRetriever = imageRetriever;
	}

	public Retriever getMaskRetriever() {
		return maskRetriever;
	}

	public void setMaskRetriever(Retriever maskRetriever) {
		this.maskRetriever = maskRetriever;
	}
}
