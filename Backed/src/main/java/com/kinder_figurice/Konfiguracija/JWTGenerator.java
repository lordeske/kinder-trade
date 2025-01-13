package com.kinder_figurice.Konfiguracija;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.kinder_figurice.Konfiguracija.SekjuritiKonstante.*;

@Component
public class JWTGenerator {



    public String generisiToken(Authentication authentication) {
        String korisnickoIme = authentication.getName();
        return generisiJWT(korisnickoIme, JWT_ISTEK);
    }


    public String generisiRefreshToken(Authentication authentication) {
        String korisnickoIme = authentication.getName();
        return generisiJWT(korisnickoIme, REFRESH_TOKEN_ISTEK);
    }


    private String generisiJWT(String subject, long expiration) {
        Date trenutniDatum = new Date();
        Date datumIsteka = new Date(trenutniDatum.getTime() + expiration);
        Key key = Keys.hmacShaKeyFor(JWT_SEC_KEY.getBytes());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(trenutniDatum)
                .setExpiration(datumIsteka)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String dobijKorisnickoImeIzJWT(String token) {
        Key key = Keys.hmacShaKeyFor(JWT_SEC_KEY.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    public boolean validisiAccessToken(String token) {
        return validisiToken(token);
    }


    public boolean validisiRefreshToken(String token) {
        return validisiToken(token);
    }


    boolean validisiToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(JWT_SEC_KEY.getBytes());
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
