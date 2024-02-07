package com.example.BlogEngine.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private Long userId; // ID dell'utente che ha scritto il commento
    private Long articleId; // ID dell'articolo a cui è collegato il commento
}
