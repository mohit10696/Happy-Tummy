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

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationMs);

        String subject = String.valueOf(user.getId());
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(claims);
        jwtBuilder.setSubject(subject);
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(expiration);
        jwtBuilder.signWith(signatureAlgorithm, jwtSecret);
        String token = jwtBuilder.compact();

        return token;

//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(signatureAlgorithm, jwtSecret)
//                .compact();


//        Map<String, Object> claims = new HashMap<>();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(String.valueOf(user.getId()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
    }

    public String validateToken(String token) {
        String username = extractUsername(token);
        if (username != null && !isTokenExpired(token)) {
            return username;
        }
        return null;
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.before(new Date());
    }

    Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    String extractUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }



}

