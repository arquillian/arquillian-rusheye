package org.jboss.lupic.parser;

import java.util.HashSet;
import java.util.Set;

import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.Test;

public abstract class Context {
	private Test currentTest;
	private Configuration currentConfiguration;
	private Set<Mask> currentMasks;
	private Set<String> maskIds = new HashSet<String>();

	public Configuration getCurrentConfiguration() {
		return currentConfiguration;
	}

	public void setCurrentConfiguration(Configuration currentConfiguration) {
		this.currentConfiguration = currentConfiguration;
	}

	public Set<Mask> getCurrentMasks() {
		return currentMasks;
	}

	public void setCurrentMasks(Set<Mask> currentMasks) {
		this.currentMasks = currentMasks;
	}

	public Test getCurrentTest() {
		return currentTest;
	}

	public void setCurrentTest(Test currentTest) {
		this.currentTest = currentTest;
	}

	public Set<String> getMaskIds() {
		return maskIds;
	}

	public abstract ParserListener getListener();
}
