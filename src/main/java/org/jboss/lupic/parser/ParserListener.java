package org.jboss.lupic.parser;

public interface ParserListener {
	void configurationParsed();

	void imageParsed();

	void suiteStarted();

	void suiteCompleted();
}
