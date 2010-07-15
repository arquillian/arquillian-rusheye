package org.jboss.lupic.retriever;

import java.awt.image.BufferedImage;
import java.util.Properties;

public class ResourceRetriever extends AbstractRetriever {

	@Override
	public BufferedImage retrieve(String source) {
		// TODO
		return null;
	}

	@Override
	public BufferedImage retrieve(Properties properties) {
		throw new UnsupportedOperationException(
				"This retriever does support only simple source retrieving");
	}
}
