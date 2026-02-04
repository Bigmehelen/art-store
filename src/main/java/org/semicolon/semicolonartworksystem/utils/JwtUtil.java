package org.semicolon.semicolonartworksystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.semicolon.semicolonartworksystem.data.models.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserPrincipal userPrincipal) {
        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expiration = new Date(currentTimeMillis + 1_216_000);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .claim("id", userPrincipal.getId())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String authToken) {
        return extractAllClaims(authToken)
                .getSubject();
    }

    public boolean isTokenValid(String authToken, UserDetails userDetails) {
        String email = extractEmail(authToken);
        return email.equals(userDetails.getUsername()) && isTokenExpired(authToken);
    }

    private boolean isTokenExpired(String authToken) {
        Date expiration = getExpiration(authToken);
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);
        return !expiration.before(nowDate);
    }

    private Date getExpiration(String authToken) {
        return extractAllClaims(authToken)
                .getExpiration();
    }

    public Claims extractAllClaims(String authToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(authToken)
                .getBody();
    }
}
