package org.jboss.lupic.exception;

public class ParserError extends Error {

    public ParserError() {
        super();
    }

    public ParserError(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserError(String message) {
        super(message);
    }

    public ParserError(Throwable cause) {
        super(cause);
    }
    
}
