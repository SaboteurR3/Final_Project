package com.example.final_project.error;

public class IncorrectInputException extends RuntimeException{
    public IncorrectInputException(String message) {
        super(message);
    }
    public IncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }
    public IncorrectInputException(Throwable cause) {
        super(cause);
    }
}
