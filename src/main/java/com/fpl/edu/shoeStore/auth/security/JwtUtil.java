package com.fpl.edu.shoeStore.auth.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final long ACCESS_EXP = 24 * 60 * 60 * 1000; // 1 Day
    private final long REFRESH_EXP = 7 * 24 * 60 * 60 * 1000; // 7 Days

    public String generateToken(String userName, int roleId, long expiration) {
        return Jwts.builder()
                .setSubject(userName)
                .claim("roleId", roleId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String generateAccessToken(String userName, int roleId) {
        return generateToken(userName, roleId, ACCESS_EXP);
    }

    public String generateRefreshToken(String userName, int roleId) {
        return generateToken(userName, roleId, REFRESH_EXP);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public int getRoleIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roleId", Integer.class);
    }
}
