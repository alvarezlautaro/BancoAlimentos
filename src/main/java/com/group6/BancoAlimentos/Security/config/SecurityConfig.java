package com.group6.BancoAlimentos.Security.config;

import com.group6.BancoAlimentos.Security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/debug/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/instituciones/**").authenticated()
                        .requestMatchers("/api/donaciones/**").hasRole("USER_DEPOSITO")
                        .requestMatchers("/api/donantes/**").hasRole("USER_DEPOSITO")
                        .requestMatchers("/api/productos/**").hasRole("USER_DEPOSITO")
                        .requestMatchers("/api/itemdonacion/**").hasRole("USER_DEPOSITO")
                        .requestMatchers("/api/remitos/**").hasRole("USER_DEPOSITO")
                        .requestMatchers("/api/detalles-remito/**").hasRole("USER_DEPOSITO")
                        .requestMatchers("/api/facturas/**").hasRole("USER_DEPOSITO")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}