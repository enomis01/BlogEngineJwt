package com.example.BlogEngine.auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;
    
}
