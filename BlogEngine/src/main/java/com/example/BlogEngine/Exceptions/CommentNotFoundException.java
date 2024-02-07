package com.example.BlogEngine.Exceptions;

public class CommentNotFoundException extends RuntimeException {
    
    public CommentNotFoundException(String message){
        super(message);
    }
}
