package com.example.university.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String secret = "my-secret-key-login-tcc"; 

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                System.out.println("Token recebido para validação: " + token);

                // Valida o token e extrai o email
                String email = JWT.require(Algorithm.HMAC256(secret))
                        .build()
                        .verify(token)
                        .getSubject();
                System.out.println("Email extraído do token: " + email);

                // Preenche o contexto de autenticação
                // SecurityContextHolder.getContext().setAuthentication(
                //         new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(email, null, null)
                // );

                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList()) 
                    // Adicionado uma lista vazia porque permite que o Spring Security reconheça a autenticação, mesmo sem permissões explícitas.
                );

                System.out.println("Contexto de autenticação preenchido com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao validar o token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            }
        } else {
            System.out.println("Nenhum token encontrado no cabeçalho Authorization.");
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("Token recebido: " + bearerToken); // Para verificação
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

