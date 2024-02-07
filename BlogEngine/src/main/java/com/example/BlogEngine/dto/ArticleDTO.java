package com.example.BlogEngine.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate publicationDate;
    private Long userId; // ID dell'utente autore dell'articolo
    private List<Long> commentIds; // Lista degli ID dei commenti sull'articolo
}
