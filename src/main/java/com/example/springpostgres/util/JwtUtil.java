package com.example.springpostgres.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

// import java.nio.charset.StandardCharsets;
// import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    // Define your SECRET_KEY, make sure it's long enough (at least 32 bytes for HS256)
    private static final String SECRET_KEY = "my-very-secret-key-that-is-long-enough-for-hmac256";

    // JWT expiration time (optional)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day

    /**
     * Method to create JWT token
     *
      * @param subject: the subject (usually the username or user ID)
     * @return JWT token as String
     */
    public String createToken(Integer penggunaId, String subject) {
        // Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Convert the SECRET_KEY to a Key object
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .subject(subject)
                .claim("penggunaId", penggunaId)
                .claim("username", subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                // .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact(); // Build the token
    }

    /**
     * Method to extract claims from the JWT token
     *
     * @param token: the JWT token
     * @return Claims object containing the token's claims
     */
    private Claims extractAllClaims(String token) {
       // Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
         
        return Jwts.parser()
                .verifyWith(key)
                // .setSigningKey(key)
                .build()
                .parseSignedClaims(token)
                // .parseClaimsJws(token)  // Menggunakan parseClaimsJws() yang sesuai
                .getPayload();
                // .getBody();
    }

    /**
     * Method to extract the subject (e.g., user ID) from the JWT token
     *
     * @param token: the JWT token
     * @return the subject (e.g., user ID or username)
     */
    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Object extractClaims(String token, String key){
        return extractAllClaims(token).get(key);
    }

    /**
     * Method to check if the JWT token is expired
     *
     * @param token: the JWT token
     * @return true if expired, false if not
     */
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Method to validate the JWT token (e.g., check expiration)
     *
     * @param token: the JWT token
     * @return true if valid, false if not
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}