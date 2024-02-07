package com.example.BlogEngine.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    //private String roles;
    private List<Long> articleIds; // Lista degli ID degli articoli scritti dall'utente
    private List<Long> commentIds; // Lista degli ID dei commenti dell'utente
}
