package org.jboss.lupic.exception;

public class ConfigurationValidationException extends ConfigurationException {

    public ConfigurationValidationException() {
        super();
    }

    public ConfigurationValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationValidationException(String message) {
        super(message);
    }

    public ConfigurationValidationException(Throwable cause) {
        super(cause);
    }
}
