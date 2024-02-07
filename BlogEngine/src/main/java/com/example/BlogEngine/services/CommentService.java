package com.example.BlogEngine.services;

import org.springframework.stereotype.Service;

import com.example.BlogEngine.dto.CommentDTO;
import com.example.BlogEngine.entities.Comment;
import com.example.BlogEngine.entities.User; // Importa la classe User
import com.example.BlogEngine.entities.Article; // Importa la classe Article
import com.example.BlogEngine.repositories.CommentRepository;
import com.example.BlogEngine.repositories.UserRepository; // Importa il repository dell'utente
import com.example.BlogEngine.repositories.ArticleRepository; // Importa il repository dell'articolo

import java.time.LocalDate;
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

    public Comment createComment(CommentDTO commentDTO, Long articleId, Long userId) {
        Comment newComment = new Comment();
        newComment.setText(commentDTO.getText());

        // Imposta la data del commento sulla data corrente come LocalDate
        newComment.setCommentDate(LocalDate.now());

        // Ottieni l'utente dal repository utilizzando l'ID dell'utente
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            newComment.setUser(user.get());

            // Ottieni l'articolo dal repository utilizzando l'ID dell'articolo
            Optional<Article> article = articleRepository.findById(articleId);
            if (article.isPresent()) {
                newComment.setArticle(article.get());

                return commentRepository.save(newComment);
            } else {
                throw new RuntimeException("L'articolo associato al commento non è stato trovato!");
            }
        } else {
            throw new RuntimeException("L'utente associato al commento non è stato trovato!");
        }
    }

    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment existingComment = optionalComment.get();
            existingComment.setText(commentDTO.getText());

            // Aggiorna l'utente se è stato fornito nell'oggetto commentDTO
            if (commentDTO.getUserId() != null) {
                Optional<User> user = userRepository.findById(commentDTO.getUserId());
                user.ifPresent(existingComment::setUser);
            }

            // Aggiorna l'articolo se è stato fornito nell'oggetto commentDTO
            if (commentDTO.getArticleId() != null) {
                Optional<Article> article = articleRepository.findById(commentDTO.getArticleId());
                article.ifPresent(existingComment::setArticle);
            }

            return commentRepository.save(existingComment);
        } else {
            throw new RuntimeException("Commento da aggiornare non trovato!");
        }
    }

    public void deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Commento da eliminare non trovato!");
        }
    }
}
