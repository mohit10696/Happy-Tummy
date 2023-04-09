package com.happytummy.happytummybackend.utils;

import com.happytummy.happytummybackend.models.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for JWT.
 */
@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    public String generateToken(User user) {

        // Create a map to store the claims (payload) for the JWT
        Map<String, Object> claims = new HashMap<>();

        // Get the current date and time
        Date now = new Date();

        // Set the expiration time for the JWT by adding the JWT expiration milliseconds to the current time
        Date expiration = new Date(now.getTime() + jwtExpirationMs);

        // Set the subject of the JWT to be the user's ID (converted to a string)
        String subject = String.valueOf(user.getId());

        // Set the signature algorithm to be used for signing the JWT
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        // Create a JWT builder object
        JwtBuilder jwtBuilder = Jwts.builder();

        jwtBuilder.setClaims(claims);
        jwtBuilder.setSubject(subject);
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(expiration);

        // Sign the JWT with the specified signature algorithm and secret key
        jwtBuilder.signWith(signatureAlgorithm, jwtSecret);

        // Compact the JWT into a string representation
        String token = jwtBuilder.compact();

        // Return the generated JWT
        return token;
    }

    /**
     * Validates a JWT token and returns the username if the token is valid and not expired.
     *
     * @param token The JWT token to be validated
     * @return The username if the token is valid and not expired, otherwise null
     */
    public String validateToken(String token) {
        String username = extractUsername(token);
        if (username != null && !isTokenExpired(token)) {
            return username;
        }
        return null;
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token The JWT token to be checked for expiration
     * @return true if the token has expired, otherwise false
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token from which to extract the expiration date
     * @return The expiration date of the token
     */
    private Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    /**
     * Extracts the username (subject) from a JWT token.
     *
     * @param token The JWT token from which to extract the username (subject)
     * @return The username (subject) of the token
     */
    private String extractUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }



}

