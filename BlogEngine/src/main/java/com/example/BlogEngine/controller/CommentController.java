package com.example.BlogEngine.controller;

import com.example.BlogEngine.Exceptions.CommentNotFoundException;
import com.example.BlogEngine.dto.CommentDTO;
import com.example.BlogEngine.entities.Comment;
import com.example.BlogEngine.services.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/create/{articleId}/{userId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long articleId, @PathVariable Long userId,
            @RequestBody CommentDTO commentDTO) {
        Comment createdComment = commentService.createComment(commentDTO, articleId, userId);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok("Commento cancellato con successo");
        } catch (CommentNotFoundException e) {
            // Se il commento non è stato trovato, restituisci un messaggio appropriato
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commento da cancellare non trovato");
        } catch (Exception e) {
            // Gestione generica di altre eccezioni
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Si è verificato un errore durante la cancellazione del commento");
        }
    }

}
