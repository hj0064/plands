package com.plands.backend.config;

import com.plands.backend.auth.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
                          OAuth2SuccessHandler oAuth2SuccessHandler,
                          OAuth2FailureHandler oAuth2FailureHandler,
                          JwtTokenProvider jwtTokenProvider) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.oAuth2FailureHandler = oAuth2FailureHandler;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/oauth2/**", "/login/**", "/api/auth/signup", "/api/auth/login").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response,
                                                 org.springframework.security.core.AuthenticationException authException)
                                    throws IOException {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            }
                        })
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                                .failureHandler(oAuth2FailureHandler)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), OAuth2LoginAuthenticationFilter.class)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/login", "/api/auth/signup"))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8082")); // 클라이언트의 주소
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 필요한 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh-Token"));
        configuration.setAllowCredentials(true); // 쿠키를 허용하려면 true로 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 CORS 적용

        return source;
    }

    // AuthenticationManager를 @Bean으로 정의
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }
}
