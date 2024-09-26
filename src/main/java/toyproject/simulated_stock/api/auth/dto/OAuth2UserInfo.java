package toyproject.simulated_stock.api.auth.dto;

import toyproject.simulated_stock.api.auth.exception.AuthException;
import toyproject.simulated_stock.api.common.utils.KeyGenerator;
import toyproject.simulated_stock.domain.member.entity.Member;
import toyproject.simulated_stock.domain.member.entity.Role;

import java.util.Map;

import static toyproject.simulated_stock.api.exception.ErrorCode.ILLEGAL_REGISTRATION_ID;


public record OAuth2UserInfo(
        String name,
        String email,
        String profile
) {
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            case "naver" -> ofNaver(attributes);
            default -> throw new AuthException(ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return new OAuth2UserInfo(
                (String) response.get("name"),
                (String) response.get("email"),
                (String) response.get("profile_image")
        );
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return new OAuth2UserInfo(
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                (String) attributes.get("picture")
        );
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return new OAuth2UserInfo(
                (String) profile.get("nickname"),
                (String) account.get("email"),
                (String) profile.get("profile_image_url")
        );
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .profile(profile)
                .memberKey(KeyGenerator.generateKey())
                .role(Role.USER)
                .build();
    }
}