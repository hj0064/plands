package com.plands.backend.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private static final String[] WHITELIST = {
            "/api/auth/signup",
            "/api/auth/login",
            "/api/auth/logout",
            "/api/auth/refresh-token",
            "/oauth2/**",
            "/login",
            "/login/**",
            "/uploads/**"
    };

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (String pattern : WHITELIST) {
            if (pathMatcher.match(pattern, path)) {
                return true; // 필터를 타지 않음
            }
        }
        return false; // 필터 실행
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        LOGGER.debug("Request URI: {}", requestURI);
        LOGGER.debug("Access Token present: {}", accessToken != null);
        LOGGER.debug("Refresh Token present: {}", refreshToken != null);

        boolean isAccessTokenValid = jwtTokenProvider.validateToken(accessToken);
        boolean isRefreshTokenValid = jwtTokenProvider.validateToken(refreshToken);

        LOGGER.debug("Access Token valid: {}", isAccessTokenValid);
        LOGGER.debug("Refresh Token valid: {}", isRefreshTokenValid);

        if (accessToken != null && isAccessTokenValid) {
            // Access Token이 유효한 경우
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.debug("Access token is valid. User authenticated: {}", authentication.getName());

        } else if (refreshToken != null && isRefreshTokenValid) {
//            // Access Token은 무효하지만 Refresh Token이 유효한 경우
//            try {
//                String newAccessToken = jwtTokenProvider.regenerateAccessToken(refreshToken);
//                jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
//                Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                LOGGER.debug("Access token regenerated using refresh token for user: {}", authentication.getName());
//            } catch (Exception e) {
//                LOGGER.error("Failed to regenerate access token", e);
//                handleUnauthorizedRequest(response, "Failed to regenerate access token");
//                return;
//            }

        } else {
            // 둘 다 유효하지 않은 경우
            LOGGER.warn("No valid token found for request: {}", requestURI);
            handleUnauthorizedRequest(response, "Unauthorized access - Token invalid or expired");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleUnauthorizedRequest(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
        response.getWriter().flush();
    }
}