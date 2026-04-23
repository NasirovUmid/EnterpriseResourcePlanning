package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.JwtAuthenticationResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtUseCase {

    private final Key secretKey;

    public JwtUseCase(@Value("${JWT_SECRET}") String jwtSecret) {

        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtAuthenticationResponseDto generateAuthToken(String email) {

        return JwtAuthenticationResponseDto.builder()
                .accessToken(generateAccessToken(email))
                .refreshToken(generateRefreshToken(email))
                .build();

    }

    public JwtAuthenticationResponseDto refreshTokens(String email) {

        return JwtAuthenticationResponseDto.builder()
                .accessToken(generateAccessToken(email))
                .refreshToken(generateRefreshToken(email))
                .build();
    }

    public String getEmailFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateJwtToken(String token) {

        try {
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;

        } catch (ExpiredJwtException expiredJwtException) {
            log.error("JWT expired!", expiredJwtException);
            throw new RuntimeException(expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.error("Unsupported jwt", unsupportedJwtException);
            throw new RuntimeException(unsupportedJwtException.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            log.error("Ugly jwt REMAKE!", malformedJwtException);
            throw new RuntimeException(malformedJwtException.getMessage());
        } catch (SecurityException securityException) {
            log.error("Security Exception", securityException);
            throw new RuntimeException(securityException.getMessage());
        } catch (Exception exception) {
            log.error("I dunno ,mb Invalid Token", exception);
            throw new RuntimeException(exception.getMessage());
        }

    }

    private String generateAccessToken(String email) {

        Date date = Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(email)
                .claim("type", TokenType.ACCESS)
                .expiration(date)
                .signWith(secretKey)
                .compact();
    }

    private String generateRefreshToken(String email) {

        Date date = Date.from(LocalDateTime.now().plusHours(5).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(email)
                .claim("type", TokenType.REFRESH)
                .expiration(date)
                .signWith(secretKey)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            return bearerToken.substring(7);

        }
        return null;
    }
}
