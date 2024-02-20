package com.example.BlogEngine.services;

import org.springframework.stereotype.Service;

import com.example.BlogEngine.auth.SecurityContext;
import com.example.BlogEngine.entities.Article;
import com.example.BlogEngine.entities.User;
import com.example.BlogEngine.exceptions.ArticleNotFoundException;
import com.example.BlogEngine.exceptions.UserNotFoundException;
import com.example.BlogEngine.exceptions.UserNotMatchingException;
import com.example.BlogEngine.repositories.ArticleRepository;
import com.example.BlogEngine.repositories.UserRepository;

import java.time.LocalDateTime;
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

    public Article createArticle(Article article) {
        Optional<User> user = userRepository.findByEmail(SecurityContext.getCurrentUsername());

        if (user.isPresent()) {
            article.setPublicationDate(LocalDateTime.now());
            article.setUser(user.get());

            return articleRepository.save(article);
        } else {
            throw new UserNotFoundException("L'utente associato all'articolo non è stato trovato!");
        }
    }

    public Article updateArticle(Long id, Article article) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            if (SecurityContext.getCurrentUsername().equals(optionalArticle.get().getUser().getEmail())
                    || SecurityContext.isCurrentUserAdmin()) {

                Article existingArticle = optionalArticle.get();
                existingArticle.setTitle(article.getTitle());
                existingArticle.setContent(article.getContent());
                existingArticle.setPublicationDate(LocalDateTime.now());

                existingArticle = articleRepository.save(existingArticle);
                return existingArticle;
            } else {
                throw new UserNotMatchingException(
                        "L'utente che vuole modificare l'articolo non corrisponde all'utente che lo ha scritto");
            }
        } else {
            throw new ArticleNotFoundException("Articolo da aggiornare non trovato!");
        }
    }

    public void deleteArticle(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            if (SecurityContext.getCurrentUsername().equals(optionalArticle.get().getUser().getEmail())
                    || SecurityContext.isCurrentUserAdmin()) {
                articleRepository.deleteById(id);
            } else {
                throw new UserNotMatchingException(
                        "L'utente che vuole eliminare l'articolo non corrisponde all'utente che lo ha scritto");
            }
        } else {
            throw new ArticleNotFoundException("L'articolo da eliminare non trovato!");
        }
    }


    public List<Article> getUserArticles() {
        // 1. dal token prendi l'email
        String email = SecurityContext.getCurrentUsername();
        // 2. dall'email prendi l'user al db
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            // 3. dall'user al db prendi l'id
            User onDb = opt.get();
            Long id = onDb.getId();

            // 4. dall'id dell'user prendi tutti gli articoli correlati -> (List<Article>)

            return articleRepository.findByUser_Id(id);
        } else {
            throw new UserNotFoundException("Questa eccezione non dovrebbe mai verificarsi");
        }
    }

    public List<Article> getArticlesByQuery(String query){
        List<Article> articleList = articleRepository.findByQuery(query);
        return articleList;
    }

}
