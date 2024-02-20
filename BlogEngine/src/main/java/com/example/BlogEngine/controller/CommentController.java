package com.example.BlogEngine.controller;

import com.example.BlogEngine.dto.CommentDTO;
import com.example.BlogEngine.entities.Comment;
import com.example.BlogEngine.factory.CommentFactory;
import com.example.BlogEngine.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDTO> response = comments.stream().map(
                CommentFactory::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/{articleId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long articleId,
            @RequestBody CommentDTO commentDTO) {
        Comment createdComment = commentService.createComment(CommentFactory.convertToEntity(commentDTO), articleId);
        CommentDTO createdCommentDTO = CommentFactory.convertToDTO(createdComment);
        return ResponseEntity.ok(createdCommentDTO);
    }

    @PutMapping("/update/{id}")     //id del commento da modificare 
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = CommentFactory.convertToEntity(commentDTO);
        updatedComment= commentService.updateComment(id, updatedComment);
        CommentDTO response = CommentFactory.convertToDTO(updatedComment);
                return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
            commentService.deleteComment(id);
            return ResponseEntity.ok("Commento cancellato con successo!");
    }

}
