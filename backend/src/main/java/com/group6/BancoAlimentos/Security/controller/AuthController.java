package com.group6.BancoAlimentos.Security.controller;

import com.group6.BancoAlimentos.Security.dto.AuthRequest;
import com.group6.BancoAlimentos.Security.dto.AuthResponse;
import com.group6.BancoAlimentos.Security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        List<String> authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList();

        String token = jwtService.generateToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token, authorities));
    }
}
