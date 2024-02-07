package com.example.BlogEngine.repositories;

import com.example.BlogEngine.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUser_Id(Long userId);
}

