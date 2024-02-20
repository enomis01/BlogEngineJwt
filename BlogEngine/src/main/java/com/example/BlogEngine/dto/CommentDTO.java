package com.example.BlogEngine.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime commentDate;
    private Long userId; // ID dell'utente che ha scritto il commento
    private String userEmail;
    private Long articleId; // ID dell'articolo a cui è collegato il commento
}
