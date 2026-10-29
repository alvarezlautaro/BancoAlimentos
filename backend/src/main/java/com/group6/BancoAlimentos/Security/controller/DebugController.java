package com.group6.BancoAlimentos.Security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping
    public ResponseEntity<Map<String, String>> get() {
        return ResponseEntity.ok(Map.of("method", "GET", "status", "OK"));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> post() {
        return ResponseEntity.ok(Map.of("method", "POST", "status", "OK"));
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> put() {
        return ResponseEntity.ok(Map.of("method", "PUT", "status", "OK"));
    }

    @PatchMapping
    public ResponseEntity<Map<String, String>> patch() {
        return ResponseEntity.ok(Map.of("method", "PATCH", "status", "OK"));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete() {
        return ResponseEntity.ok(Map.of("method", "DELETE", "status", "OK"));
    }
}