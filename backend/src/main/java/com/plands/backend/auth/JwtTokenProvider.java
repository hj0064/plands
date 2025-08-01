package com.plands.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 토큰 생성
    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 권한이 비어 있다면 기본값으로 "ROLE_USER"를 설정
        if (authorities.isEmpty()) {
            authorities = "ROLE_USER";
        }
        String accessToken = createAccessToken(authentication, authorities);
        String refreshToken = createRefreshToken(authentication, authorities);

        LOGGER.debug("generate Token success");

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // AccessToken 생성 - 30분으로 수정
    public String createAccessToken(Authentication authentication, String authorities) {
        return Jwts.builder()
                .signWith(key)
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30분
                .compact();
    }

    // RefreshToken 생성 - 7일로 수정
    public String createRefreshToken(Authentication authentication, String authorities) {
        return Jwts.builder()
                .signWith(key)
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일
                .compact();
    }

    // 토큰에서 Authentication 객체 추출
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보 없는 토큰 입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        LOGGER.debug("getAuthentication Success");

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            LOGGER.debug("Token is null or empty");
            return false;
        }

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            LOGGER.debug("token is valid");
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Token expired", e);
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT", e);
        } catch (SecurityException e) {
            LOGGER.error("JWT Security exception", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT Illegal argument", e);
        }
        return false;
    }

    // Claims 추출
    private Claims parseClaims(String accessToken) {
        try {
            LOGGER.debug("parse claim success");
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // AccessToken 추출
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);// "Bearer " 이후의 부분을 반환
        }
        return null;
    }

    // RefreshToken 추출 - 쿠키에서도 찾아보도록 수정
    public String resolveRefreshToken(HttpServletRequest request) {
        // 헤더에서 먼저 찾기
        String refreshToken = request.getHeader("Refresh-Token");
        if (refreshToken != null && !refreshToken.trim().isEmpty()) {
            return refreshToken;
        }

        // 쿠키에서 찾기 (선택사항)
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        LOGGER.debug("No refresh token found in headers or cookies");
        return null;
    }

    // RefreshToken으로 새로운 AccessToken 생성 - 수정
    public String regenerateAccessToken(String refreshToken) {
        Claims claims = parseClaims(refreshToken);
        String subject = claims.getSubject();
        String authorities = claims.get("auth").toString();

        // Authentication 객체 재생성
        Collection<? extends GrantedAuthority> authoritiesList =
                Arrays.stream(authorities.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(subject, "", authoritiesList);

        return createAccessToken(authentication, authorities);
    }

    // 새로운 AccessToken을 Response Header에 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer " + accessToken);
    }
}