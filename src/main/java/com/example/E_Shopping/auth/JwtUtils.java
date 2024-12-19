package com.example.E_Shopping.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import com.example.E_Shopping.model.Users.Role;

@Component
public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 ngày

    // Tạo token với thông tin userId, username và role
    public String generateToken(Long userId, String username, Role role) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // ID người dùng làm subject
                .claim("username", username) // Lưu username vào payload
                .claim("role", role.name()) // Lưu role vào payload
                .setIssuedAt(new Date()) // Ngày phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Hạn token
                .signWith(key) // Ký token bằng key bí mật
                .compact();
    }

    // Trích xuất role từ token
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // Trích xuất username từ token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class); // Lấy từ claim "username"
    }

    // Trích xuất userId (subject) từ token
    public Long extractUserId(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(subject); // Chuyển subject về kiểu long
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
