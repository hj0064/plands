package com.plands.backend.auth;

import com.plands.backend.dto.MemberDto;
import com.plands.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    public CustomOAuth2UserService(@Lazy MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2UserRequest에서 OAuth2 인증 정보를 가져와 사용자 정보를 로드
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        LOGGER.debug("user loaded");

        // OAuth2 사용자 정보에서 필요한 값 추출
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes 객체로 변환
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 이메일을 기반으로 사용자 조회
        Optional<MemberDto> member = memberService.findByEmail(oAuthAttributes.getEmail());
        if (member.isEmpty()) {
            // 기존에 없으면 새로운 사용자로 등록
            member = Optional.of(memberService.saveOAuthMember(oAuthAttributes.toEntity()));
            LOGGER.debug("New member saved: {}", member.get().getName());
        }

        LOGGER.debug("member loading succeeded");

        // 인증된 사용자 정보를 DefaultOAuth2User로 반환 (권한 설정 포함)
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.get().getRole().toString())),
                oAuthAttributes.getAttributes(),
                oAuthAttributes.getNameAttributeKey()
        );
    }
}
