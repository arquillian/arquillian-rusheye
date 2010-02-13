package org.jboss.lupic.retriever;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

public class FileRetriever extends AbstractRetriever {
	
	public BufferedImage retrieve(String source) {
		String baseDirectory = (String) getProperties().get("base-directory");
		
		File file;
		
		if (baseDirectory == null) {
			file = new File(source);
		} else {
			file = new File(baseDirectory, source);
		}
		
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			throw new IllegalArgumentException("Source is unreachable - " + file);
		}
	}

	public BufferedImage retrieve(Properties properties) {
		throw new UnsupportedOperationException("This retriever does support only simple source retrieving");
	}
}
