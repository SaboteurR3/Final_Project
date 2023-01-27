package com.example.final_project.error;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
    public LoginException(Throwable cause) {
        super(cause);
    }
}
