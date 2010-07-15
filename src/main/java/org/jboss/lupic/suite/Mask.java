package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.jboss.lupic.retriever.Retriever;

public class Mask extends FutureTask<BufferedImage> {

	private String id;
	private VerticalAlignment verticalAlignment;
	private HorizontalAlignment horizontalAlignment;

	public Mask(String id, final String source,
			final Properties maskProperties, final Retriever retriever,
			VerticalAlignment verticalAlignment,
			HorizontalAlignment horizontalAlignment) {
		super(new Callable<BufferedImage>() {
			@Override
			public BufferedImage call() throws Exception {
				return retriever.retrieve(source, maskProperties);
			}
		});
		this.id = id;
		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mask other = (Mask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
