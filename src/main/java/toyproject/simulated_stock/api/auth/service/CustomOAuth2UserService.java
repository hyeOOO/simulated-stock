package toyproject.simulated_stock.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.api.auth.dto.OAuth2UserInfo;
import toyproject.simulated_stock.api.auth.dto.model.PrincipalDetails;
import toyproject.simulated_stock.domain.member.entity.Member;
import toyproject.simulated_stock.domain.member.repository.MemberRepository;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;
import toyproject.simulated_stock.domain.stock.order.repository.UserAccountRepository;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);
        Member member = getOrSave(oAuth2UserInfo);

        return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
    }

    private Member getOrSave(OAuth2UserInfo oAuth2UserInfo) {
        Member member = memberRepository.findByEmail(oAuth2UserInfo.email())
                .orElseGet(()->{
                    // 존재하지 않으면 새로운 회원 생성
                    Member newMember = oAuth2UserInfo.toEntity();

                    // 새로운 회원과 연관된 UserAccount 생성
                    UserAccount userAccount = UserAccount.createAccount(newMember, new BigDecimal("10000000")); // 1000만원 설정
                    userAccountRepository.save(userAccount);

                    return newMember;
                });
        return memberRepository.save(member);
    }
}