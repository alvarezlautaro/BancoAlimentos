package com.group6.BancoAlimentos.Common.dto;

import java.time.LocalDateTime;

public record RevisionDTO<T>(Long revision, LocalDateTime fecha, String tipo, T datos){
}
