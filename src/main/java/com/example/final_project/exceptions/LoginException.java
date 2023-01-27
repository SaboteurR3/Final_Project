package com.example.final_project.exceptions;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
    public LoginException() {
    }
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
    public LoginException(Throwable cause) {
        super(cause);
    }
}
