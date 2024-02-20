package com.example.BlogEngine.factory;

import com.example.BlogEngine.dto.CommentDTO;
import com.example.BlogEngine.entities.Comment;

public class CommentFactory {
    public static CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setUserEmail(comment.getUser().getEmail());
        commentDTO.setArticleId(comment.getArticle().getId());
        commentDTO.setCommentDate(comment.getCommentDate());
        return commentDTO;
    }

    public static Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        // Non impostiamo direttamente l'utente e l'articolo qui, dovremmo farlo tramite
        // servizio
        // Altri campi se necessario
        return comment;
    }
}
