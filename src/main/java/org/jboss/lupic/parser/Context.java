package org.jboss.lupic.parser;

import java.util.HashSet;
import java.util.Set;

import org.jboss.lupic.suite.Mask;

public abstract class Context {
	private Set<Mask> currentMasks;
	private Set<String> maskIds = new HashSet<String>();

	public Set<Mask> getCurrentMasks() {
		return currentMasks;
	}

	public void setCurrentMasks(Set<Mask> currentMasks) {
		this.currentMasks = currentMasks;
	}

	public Set<String> getMaskIds() {
		return maskIds;
	}
	
	public abstract ParserListener getListener();
}
