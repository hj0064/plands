package com.plands.backend.auth;

import java.io.IOException;

import com.plands.backend.dto.MemberDto;
import com.plands.backend.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2SuccessHandler.class);

    @Autowired
    public OAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider, @Lazy MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        clearAuthenticationAttributes(request, response);

        LOGGER.debug("authentication succeeded and redirect");
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
    // Cookies 사용
//    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        Optional<String> cookie = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue);
//        String targetUrl = cookie.orElse(getDefaultTargetUrl());
//
//        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
//
//        String accessToken = jwtToken.getAccessToken();
//        String refreshToken = jwtToken.getRefreshToken();
//
//        // ✅ 쿠키로 토큰 저장 (JS에서 접근 가능하게 HttpOnly false)
//        CookieUtils.addCookie(response, "accessToken", accessToken, 60 * 60); // 1시간
//        CookieUtils.addCookie(response, "refreshToken", refreshToken, 7 * 24 * 60 * 60); // 7일
//
//        return UriComponentsBuilder.fromUriString(targetUrl)
//                .build().toUriString(); // ❌ 더 이상 토큰은 URL에 포함하지 않음
//    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> cookie = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = cookie.orElse(getDefaultTargetUrl());

        // 사용자 정보 조회
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        MemberDto member = memberService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

        // 토큰 발급
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // 사용자 정보 + 토큰을 리다이렉트 URI에 포함
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", jwtToken.getAccessToken())
                .queryParam("refreshToken", jwtToken.getRefreshToken())
                .queryParam("memberId", member.getMemberId())
                .queryParam("nickname", member.getNickname())
                .queryParam("profileImageUrl", member.getProfileImageUrl())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        LOGGER.debug("clear authentication attributes");
        removeAuthorizationRequestCookies(request, response);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("remove authorization request cookies");
        String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}
