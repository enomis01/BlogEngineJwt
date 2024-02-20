package com.example.BlogEngine.dto;

import java.util.List;


import com.example.BlogEngine.entities.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    private String username;
    @NotNull
    private String password;
    private String email;
    @NotNull
    private Role role;
    //private String roles;
    private List<Long> articleIds; // Lista degli ID degli articoli scritti dall'utente
    private List<Long> commentIds; // Lista degli ID dei commenti dell'utente


}
