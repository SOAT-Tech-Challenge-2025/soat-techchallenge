package com.store.soattechchallenge.identification.infrastructure.security;

import com.store.soattechchallenge.identification.domain.entities.Identification;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class JwtTokenSecurity {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Identification identification) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id_client", identification.getId());
        claims.put("email", identification.getEmail());
        claims.put("documentNumber", identification.getNumberDocument());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(identification.getId()))
                .setSubject(identification.getNumberDocument())
                .setSubject(identification.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getDocumentOrEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, Optional<Identification> identification) {
        final String documentOrEmail = getDocumentOrEmailFromToken(token);
        return (identification.isPresent() && (documentOrEmail.equals(identification.get().getNumberDocument()))
                || (identification.isPresent() && documentOrEmail.equals(identification.get().getEmail())) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}