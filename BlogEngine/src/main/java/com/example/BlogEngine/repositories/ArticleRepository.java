package com.example.BlogEngine.repositories;

import com.example.BlogEngine.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUser_Id(Long userId);

    @Query(value = "SELECT * FROM articles WHERE title LIKE %:param% OR content LIKE %:param%", nativeQuery = true)
    List<Article> findByQuery(@Param("param") String param);
}
