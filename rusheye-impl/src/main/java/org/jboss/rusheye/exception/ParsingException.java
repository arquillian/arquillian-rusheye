package org.jboss.rusheye.exception;

public class ParsingException extends RuntimeException {

    public ParsingException() {
        super();
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(Throwable cause) {
        super(cause);
    }
}
