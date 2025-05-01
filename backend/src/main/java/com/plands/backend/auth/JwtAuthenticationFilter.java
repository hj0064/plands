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
            "/oauth2/**",
            "/login",
            "/login/**"
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
    // TODO: refreshtoken이 발급이 안되는디
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        boolean isAccessTokenValid = jwtTokenProvider.validateToken(accessToken);
        boolean isRefreshTokenValid = jwtTokenProvider.validateToken(refreshToken);

        if (accessToken != null && isAccessTokenValid) {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.debug("Access token is valid. User is authenticated.");
        } else if (refreshToken != null && isRefreshTokenValid) {
            String newAccessToken = jwtTokenProvider.regenerateAccessToken(refreshToken);
            jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
            Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.debug("Access token regenerated using refresh token.");
        } else {
            LOGGER.warn("No valid token found. User will be logged out.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access - Token invalid or expired");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
