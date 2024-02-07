package com.example.BlogEngine.services;

import org.springframework.stereotype.Service;
import com.example.BlogEngine.dto.ArticleDTO;
import com.example.BlogEngine.entities.Article;
import com.example.BlogEngine.entities.User;
import com.example.BlogEngine.repositories.ArticleRepository;
import com.example.BlogEngine.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(ArticleDTO articleDTO, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Article article = new Article();
            article.setTitle(articleDTO.getTitle());
            article.setContent(articleDTO.getContent());
            article.setPublicationDate(LocalDate.now());
            article.setUser(user);

            return articleRepository.save(article);
        } else {
            throw new EntityNotFoundException("L'utente con ID " + userId + " non è stato trovato.");
        }
    }

    public Article updateArticle(Long id, ArticleDTO articleDTO) {
        Optional<Article> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isPresent()) {
            Article existingArticle = optionalArticle.get();
            existingArticle.setTitle(articleDTO.getTitle());
            existingArticle.setContent(articleDTO.getContent());
            if (articleDTO.getUserId() != null) {
                User user = userRepository.findById(articleDTO.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
                existingArticle.setUser(user);
            }

            return articleRepository.save(existingArticle);
        } else {
            throw new RuntimeException("Articolo da aggiornare non trovato!");
        }
    }

    public void deleteArticle(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Articolo da eliminare non trovato!");
        }
    }
}
