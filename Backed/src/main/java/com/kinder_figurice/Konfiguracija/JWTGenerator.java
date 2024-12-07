package com.kinder_figurice.Konfiguracija;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.kinder_figurice.Konfiguracija.SekjuritiKonstante.JWT_ISTEK;
import static com.kinder_figurice.Konfiguracija.SekjuritiKonstante.JWT_SEC_KEY;

@Component
public class JWTGenerator {


    public String generisiToken(Authentication authentication) {
        String korisnickoIme = authentication.getName();
        Date trenutniDatum = new Date();
        Date datumIsteka = new Date(trenutniDatum.getTime() + JWT_ISTEK);


        Key key = Keys.hmacShaKeyFor(JWT_SEC_KEY.getBytes());

        // Kreiranje i potpisivanje tokena
        return Jwts.builder()
                .setSubject(korisnickoIme)
                .setIssuedAt(trenutniDatum)
                .setExpiration(datumIsteka)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Metoda za dobijanje korisničkog imena iz JWT tokena
    public String dobijKorisnickoImeIzJWT(String token) {
        Key key = Keys.hmacShaKeyFor(JWT_SEC_KEY.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validisiToken(String token) {
        Key key = Keys.hmacShaKeyFor(JWT_SEC_KEY.getBytes());

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (MalformedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Neispravan JWT format.");
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token je istekao.");
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token nije podržan.");
        } catch (IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token je prazan ili nevalidan.");
        }
    }



}
