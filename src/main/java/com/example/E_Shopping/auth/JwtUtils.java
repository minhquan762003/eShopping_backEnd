package com.example.E_Shopping.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 ngày


    public String generateToken(Long userId, String username,String email, Set<String> roles, Date birthDay, String number,int gender, Date createdAt, Date updatedAt, String name) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) 
                .claim("username", username) 
                .claim("email", email)
                .claim("role", new ArrayList<>(roles)) 
                .claim("birthDay", birthDay.getTime())
                .claim("number", number)
                .claim("gender", gender)
                .claim("name", name)
                .claim("createdAt", createdAt.getTime())
                .claim("updatedAt", updatedAt.getTime())

                .setIssuedAt(new Date()) // Ngày phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Hạn token
                .signWith(key) 
                .compact();
    }

    // Trích xuất role từ token
    public List<String> extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", List.class);
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class); 
    }


    public Long extractUserId(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(subject); 
    }

    // Xác thực token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true; // Token hợp lệ
        } catch (ExpiredJwtException e) {
            System.err.println("Token đã hết hạn: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token không đúng định dạng: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("Chữ ký không hợp lệ: " + e.getMessage());
        } catch (JwtException e) {
            System.err.println("Token không hợp lệ: " + e.getMessage());
        }
        return false; // Token không hợp lệ 
    }
}
