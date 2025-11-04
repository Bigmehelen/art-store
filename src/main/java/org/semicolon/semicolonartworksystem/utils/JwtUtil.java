package org.semicolon.semicolonartworksystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.semicolon.semicolonartworksystem.data.models.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

@Service
public class JwtUtil {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(UserPrincipal userPrincipal) {
        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expiration = new Date(currentTimeMillis + 300_000);



        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .claim("id", userPrincipal.getId())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String validateTokenAndGetUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }


    public String extractEmail(String authToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();

    }

    public boolean isTokenValid(String authToken, UserDetails  userDetails) {
        String email = extractEmail(authToken);
        return email.equals(userDetails.getUsername()) && isTokenExpired(authToken);
    }

    private boolean isTokenExpired(String authToken) {
        Date expiryDate = getExpiration(authToken);
        return expiryDate.before(new Date());
    }

    private Date getExpiration(String authToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(authToken)
                .getBody()
                .getExpiration();
    }

    public Claims extractAllClaims(String authToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(authToken)
                .getBody();
    }
}
