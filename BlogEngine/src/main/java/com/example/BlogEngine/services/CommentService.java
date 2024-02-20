package com.example.BlogEngine.services;

import org.springframework.stereotype.Service;

import com.example.BlogEngine.auth.SecurityContext;
import com.example.BlogEngine.entities.Comment;
import com.example.BlogEngine.entities.User;
import com.example.BlogEngine.exceptions.ArticleNotFoundException;
import com.example.BlogEngine.exceptions.CommentNotFoundException;
import com.example.BlogEngine.exceptions.UserNotFoundException;
import com.example.BlogEngine.exceptions.UserNotMatchingException;
import com.example.BlogEngine.entities.Article;
import com.example.BlogEngine.repositories.CommentRepository;
import com.example.BlogEngine.repositories.UserRepository;
import com.example.BlogEngine.repositories.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
            ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment, Long articleId) {
        // Ottieni l'utente dal repository utilizzando il token
        Optional<User> user = userRepository.findByEmail(SecurityContext.getCurrentUsername());

        // Optional<User> user = userRepository.findById((long) 31);
        if (user.isPresent()) {
            // Ottieni l'articolo dal repository utilizzando l'ID dell'articolo
            Optional<Article> article = articleRepository.findById(articleId);
            if (article.isPresent()) {
                comment.setCommentDate(LocalDateTime.now());
                comment.setUser(user.get());
                comment.setArticle(article.get());

                return commentRepository.save(comment);
            } else {
                throw new ArticleNotFoundException("L'articolo associato al commento non è stato trovato!");
            }
        } else {
            throw new UserNotFoundException("L'utente associato al commento non è stato trovato!");
        }
    }

    public Comment updateComment(Long id, Comment comment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {

            if (SecurityContext.getCurrentUsername().equals(optionalComment.get().getUser().getEmail())) {

                Comment existingComment = optionalComment.get();
                existingComment.setText(comment.getText());
                existingComment.setCommentDate(LocalDateTime.now());

                existingComment = commentRepository.save(existingComment);

                return existingComment;
            } else {
                throw new UserNotMatchingException(
                        "L'utente che vuole modificare il commento non corrisponde all'utente che lo ha scritto!");
            }
        } else {
            throw new CommentNotFoundException("Commento da aggiornare non trovato!");
        }
    }

    public void deleteComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            if (SecurityContext.getCurrentUsername().equals(optionalComment.get().getUser().getEmail())
                    || SecurityContext.isCurrentUserAdmin()) {
                commentRepository.deleteById(id);
            } else {
                throw new UserNotMatchingException( 
                        "L'utente che vuole eliminare il commento non corrisponde all'utente che lo ha scritto");
            }
        } else {
            throw new CommentNotFoundException("Commento da eliminare non trovato!");
        }
    }

    public List<Comment> getUserComments() {
      String email = SecurityContext.getCurrentUsername();
      Optional<User> opt = userRepository.findByEmail(email);
      if (opt.isPresent()) {
        User onDb = opt.get();
        Long id = onDb.getId();
        return commentRepository.findByUserId(id);
    } else {
        throw new UserNotFoundException("Questa eccezione non dovrebbe mai verificarsi");
    }
}

}
