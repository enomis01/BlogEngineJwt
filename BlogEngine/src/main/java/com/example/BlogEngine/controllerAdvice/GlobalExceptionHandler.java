package com.example.BlogEngine.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.BlogEngine.exceptions.ArticleNotFoundException;
import com.example.BlogEngine.exceptions.CommentNotFoundException;
import com.example.BlogEngine.exceptions.UserNotFoundException;
import com.example.BlogEngine.exceptions.UserNotMatchingException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
            }
            
            @ExceptionHandler(ArticleNotFoundException.class)
            public ResponseEntity<String> handleArticleNotFoundException(ArticleNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
            }
            
            @ExceptionHandler(CommentNotFoundException.class)
            public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autenticazione fallita");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non hai il ruolo adeguato per la chiamata!");
    }

    @ExceptionHandler(UserNotMatchingException.class)
    public ResponseEntity<String> handleUserNotMatchingException(UserNotMatchingException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}

