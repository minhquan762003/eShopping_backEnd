package com.example.E_Shopping.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter)
            throws Exception {
        return http.csrf().disable()
                .cors() // Enable CORS
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/users/login", "/api/users/register", "/api/products/getAllProducts").permitAll()
                .requestMatchers("/api/users").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/products/addProduct").hasAnyAuthority("SELLER", "ADMIN")
                .anyRequest().hasAnyAuthority("USER", "ADMIN")
                .and()
                .rememberMe() // Kích hoạt Remember Me
                .key("R3m3mb3rM3$ecureKey") // Secret key mã hóa token Remember Me
                .rememberMeCookieName("myRememberMeCookie") // Tên cookie lưu trữ token
                .tokenValiditySeconds(7 * 24 * 60 * 60) // Hiệu lực cookie: 7 ngày
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS configuration bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
