package com.group6.BancoAlimentos.Security.dto;

import java.util.List;

public record AuthResponse(String token, List<String> roles) {}
