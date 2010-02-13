package org.jboss.lupic.retriever;

import java.awt.image.BufferedImage;
import java.util.Properties;

public interface Retriever {
	
	public BufferedImage retrieve(String source);

	public BufferedImage retrieve(Properties properties);
}
