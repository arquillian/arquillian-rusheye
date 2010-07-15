package org.jboss.lupic.suite;

import java.util.HashSet;
import java.util.Set;

import org.jboss.lupic.retriever.Retriever;

public class GlobalConfiguration {
	Retriever imageRetriever;
	Perception perception = new Perception();
	Set<Mask> ignoreBitmapMasks = new HashSet<Mask>();
	Set<Mask> selectiveAlphaMasks = new HashSet<Mask>();

	public Retriever getImageRetriever() {
		return imageRetriever;
	}

	public void setImageRetriever(Retriever imageRetriever) {
		this.imageRetriever = imageRetriever;
	}

	public Perception getPerception() {
		return perception;
	}

	public void setPerception(Perception perception) {
		this.perception = perception;
	}

	public Set<Mask> getIgnoreBitmapMasks() {
		return ignoreBitmapMasks;
	}

	public void setIgnoreBitmapMasks(Set<Mask> ignoreBitmapMasks) {
		this.ignoreBitmapMasks = ignoreBitmapMasks;
	}

	public Set<Mask> getSelectiveAlphaMasks() {
		return selectiveAlphaMasks;
	}

	public void setSelectiveAlphaMasks(Set<Mask> selectiveAlphaMasks) {
		this.selectiveAlphaMasks = selectiveAlphaMasks;
	}
}
