package com.kinder_figurice.Konfiguracija;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}



/// Ovo nam sluzi da mi postavljamo nase
// Default ponašanje: Ako ne definišeš prilagođeni AuthenticationEntryPoint, Spring Security vraća HTTP status 401 Unauthorized sa generičkom porukom.
// Prilagođavanje: Mozemo koristiti commence za prilagođeni odgovor kada klijent nije autentifikovan, kasnije ga koristimo u Configuraciji