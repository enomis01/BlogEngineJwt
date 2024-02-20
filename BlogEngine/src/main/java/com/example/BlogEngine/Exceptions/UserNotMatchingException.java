package com.example.BlogEngine.exceptions;

public class UserNotMatchingException extends RuntimeException {
    public UserNotMatchingException(String message) {
        super(message);
    }
}
