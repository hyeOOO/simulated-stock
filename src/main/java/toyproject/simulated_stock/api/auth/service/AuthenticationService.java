package toyproject.simulated_stock.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.api.auth.exception.AuthException;
import toyproject.simulated_stock.domain.member.entity.Member;
import toyproject.simulated_stock.domain.member.repository.MemberRepository;

import static toyproject.simulated_stock.api.exception.ErrorCode.MEMBER_NOT_FOUND;
import static toyproject.simulated_stock.api.exception.ErrorCode.NO_ACCESS;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final MemberRepository memberRepository;

    public Member getMemberOrThrow(String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
                .orElseThrow(() -> new AuthException(MEMBER_NOT_FOUND));
    }

    public void checkAccess(String memberKey, Member member) {
        if (!member.getMemberKey().equals(memberKey)) {
            throw new AuthException(NO_ACCESS);
        }
    }
}
