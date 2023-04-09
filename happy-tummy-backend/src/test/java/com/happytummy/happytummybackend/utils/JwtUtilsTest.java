package com.happytummy.happytummybackend.utils;

import com.happytummy.happytummybackend.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @Test
    void generateTokenTest() {
        User user = new User(12L, "saass", "adadsa", "asdadssa", "sadsdad", "asdasdasd", "asdfasdf", "asasd", "121231", "asasasds");
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtils.generateToken(user);
        String generatedToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        assertEquals(generatedToken, token);
    }

    @Test
    void validateTokenTest(){
        User user = new User(12L, "saass", "adadsa", "asdadssa", "sadsdad", "asdasdasd", "asdfasdf", "asasd", "121231", "asasasds");
        Map<String, Object> claims = new HashMap<>();
        //JwtUtils jwtUtils = createJwtUtils();

        //JwtUtils jwtUtils = new JwtUtils();
        JwtService jwtService = new JwtService(jwtUtils);
        String token = jwtService.generateToken(user);
        String username = jwtService.validateToken(token);

        //String token = jwtUtils.generateToken(user);
        //String username = jwtUtils.validateToken(token);
        assertEquals(username, String.valueOf(user.getId()));
    }


    @Test
    void isTokenExpiredTest(){
        User user = new User(12L, "saass", "adadsa", "asdadssa", "sadsdad", "asdasdasd", "asdfasdf", "asasd", "121231", "asasasds");
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtils.generateToken(user);
        Date expirationDate = jwtUtils.extractExpiration(token);
        boolean isExpired = expirationDate.before(new Date());
        assertFalse(isExpired);
    }


    @Test
    void extractExpirationTest(){
        User user = new User(12L, "saass", "adadsa", "asdadssa", "sadsdad", "asdasdasd", "asdfasdf", "asasd", "121231", "asasasds");
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtils.generateToken(user);
        Date expirationDate = jwtUtils.extractExpiration(token);
        Date generatedExpirationDate = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
        assertEquals(generatedExpirationDate, expirationDate);
    }

//    private String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//    }

    @Test
    void extractUsernameTest(){
        User user = new User(12L, "saass", "adadsa", "asdadssa", "sadsdad", "asdasdasd", "asdfasdf", "asasd", "121231", "asasasds");
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtils.generateToken(user);
        String username = jwtUtils.extractUsername(token);
        String generatedUsername = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        assertEquals(generatedUsername, username);
    }




}