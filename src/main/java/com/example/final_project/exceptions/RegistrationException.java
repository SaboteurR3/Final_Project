package com.example.final_project.exceptions;

public class RegistrationException extends RuntimeException{
    public RegistrationException(String message) {
        super(message);
    }
    public RegistrationException() {
    }
    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
    public RegistrationException(Throwable cause) {
        super(cause);
    }
}
