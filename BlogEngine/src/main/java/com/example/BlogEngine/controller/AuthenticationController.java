package com.example.BlogEngine.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.BlogEngine.auth.AuthenticationRequest;
import com.example.BlogEngine.auth.AuthenticationResponse;
import com.example.BlogEngine.auth.AuthenticationService;
import com.example.BlogEngine.auth.RegisterRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

// @GetMapping("/migration")
// public void migration(){
//     userService.migration();
// }
}



