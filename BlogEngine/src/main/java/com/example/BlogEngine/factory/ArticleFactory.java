package com.example.BlogEngine.factory;

import com.example.BlogEngine.dto.ArticleDTO;
import com.example.BlogEngine.entities.Article;

public class ArticleFactory {
    public static ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUserId(article.getUser().getId());
        articleDTO.setUserEmail(article.getUser().getEmail());
        articleDTO.setPublicationDate(article.getPublicationDate());

        // Altri campi se necessario
        return articleDTO;
    }

    public static Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        // Non impostiamo direttamente l'utente e i commenti qui, dovremmo farlo tramite
        // servizio
        return article;
    }
}
