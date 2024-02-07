package com.example.BlogEngine.factory;

import java.util.stream.Collectors;

import com.example.BlogEngine.dto.ArticleDTO;
import com.example.BlogEngine.entities.Article;
import com.example.BlogEngine.entities.Comment;

public class ArticleFactory {
    public static ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUserId(article.getUser().getId());
        // Imposta gli ID dei commenti sull'articolo
        articleDTO.setCommentIds(article.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
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
