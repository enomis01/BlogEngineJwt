package com.example.BlogEngine.auth;

import com.example.BlogEngine.entities.User;
import com.example.BlogEngine.exceptions.UserNotFoundException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BlogEngine.config.JwtService;
import com.example.BlogEngine.entities.Role;
import com.example.BlogEngine.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("UserNotFound"));

                try{
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        request.getPassword()));
                        
                } catch (UsernameNotFoundException ex){
                        throw new UserNotFoundException("UsernameNotFound");
                }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
