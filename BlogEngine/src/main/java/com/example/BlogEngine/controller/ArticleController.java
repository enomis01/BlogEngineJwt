package com.example.BlogEngine.controller;

import com.example.BlogEngine.dto.ArticleDTO;
import com.example.BlogEngine.entities.Article;
import com.example.BlogEngine.factory.ArticleFactory;
import com.example.BlogEngine.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<ArticleDTO> response = articles.stream().map(
                ArticleFactory::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        return article.map(inner -> ResponseEntity.ok(ArticleFactory.convertToDTO(inner)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        Article createdArticle = articleService.createArticle(ArticleFactory.convertToEntity(articleDTO));
        ArticleDTO createdArticleDTO = ArticleFactory.convertToDTO(createdArticle);
        return ResponseEntity.ok(createdArticleDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        Article updatedArticle = ArticleFactory.convertToEntity(articleDTO);
        updatedArticle = articleService.updateArticle(id, updatedArticle);

        ArticleDTO response = ArticleFactory.convertToDTO(updatedArticle);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok("Articolo cancellato con successo!");
    }

    @GetMapping("/own")
    public ResponseEntity<List<ArticleDTO>> getUserArticles() {
        List<Article> articleList = articleService.getUserArticles();
        List<ArticleDTO> response = articleList.stream().map(
                ArticleFactory::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
   
    @GetMapping("/by-query/{queryParam}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByQuery(@PathVariable("queryParam") String query){
        List<Article> articleList = articleService.getArticlesByQuery(query);
        List<ArticleDTO> response = articleList.stream().map(
            ArticleFactory::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(response);
    }
    
}
