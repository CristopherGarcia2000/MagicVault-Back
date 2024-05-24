package com.magicvault.jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.magicvault.documents.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// Service class responsible for JWT token generation and validation.
@Service
public class JWTService {

    // Secret key used for JWT token signing.
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    // Method to generate a JWT token for the provided user details.
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    // Method to generate a JWT token with extra claims for the provided user details.
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        Users currentUser = (Users) user;
        extraClaims.put("username", currentUser.getUsername());
        extraClaims.put("email", currentUser.getEmail());
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // Method to retrieve the secret key for JWT signing.
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method to extract the username from a JWT token.
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Method to check if a JWT token is valid for the provided user details.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Method to parse all claims from a JWT token.
    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // Method to retrieve a specific claim from a JWT token.
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to retrieve the expiration date of a JWT token.
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Method to check if a JWT token has expired.
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}