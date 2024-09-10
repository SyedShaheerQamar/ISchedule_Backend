package com.schedule.ISchedule.service.impl;

import com.schedule.ISchedule.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements IJwtService {

    private final Key SECRET_KEY = getSigning();

    @Override
    public String generateTokenWithClaims(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // Set the claims (email, role, etc.)
                .setSubject(userDetails.getUsername()) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours expiration
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Sign with secret key
                .compact();
    }

    // Extract the username (subject) from the token
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    // Extract the email from the token
    public String extractEmail(String token) {
        return extractClaims(token, claims -> claims.get("email", String.class));
    }

    // Extract specific claims
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Signing key generation
    private Key getSigning() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Check token expiration
    public Date getExpirationDateFromToken(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    // Validate the token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
