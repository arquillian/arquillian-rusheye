package org.jboss.lupic.retriever;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

public class FileRetriever extends AbstractRetriever {
	
	public BufferedImage retrieve(String source, Properties localProperties) {
		Properties properties = mergeProperties(localProperties);
		String baseDirectory = (String) properties.get("base-directory");
		
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
}
