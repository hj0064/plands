package com.plands.backend.auth;

import com.plands.backend.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;
    private String providerId;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKakao(userNameAttributeName, attributes);
        }
        else if (registrationId.equals("naver")) {
            return ofNaver(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider("google")
                .providerId((String) attributes.get("sub"))  // Google에서 제공하는 사용자 고유 ID
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider("naver")
                .providerId((String) response.get("id"))  // Naver에서 제공하는 사용자 고유 ID
                .build();
    }

    private static OAuthAttributes ofKakao(String usernameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        return OAuthAttributes.builder()
                .name((String) account.get("nickname"))
                .email((String) account.get("email"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .provider("kakao")  // provider 추가
                .providerId((String) account.get("id"))  // Kakao에서 제공하는 사용자 고유 ID
                .build();
    }

    public MemberDto toEntity() {
        return MemberDto.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .role(Role.ROLE_USER)
                .build();
    }

}
