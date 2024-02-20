package com.example.BlogEngine.dto;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime publicationDate;
    private Long userId; // ID dell'utente autore dell'articolo
    private String userEmail;
} 