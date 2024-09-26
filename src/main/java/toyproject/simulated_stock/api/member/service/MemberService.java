package toyproject.simulated_stock.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.api.member.dto.MemberDto;
import toyproject.simulated_stock.api.member.dto.MemberEditRequest;
import toyproject.simulated_stock.api.member.exception.MemberException;
import toyproject.simulated_stock.domain.member.entity.Member;
import toyproject.simulated_stock.domain.member.repository.MemberRepository;

import static toyproject.simulated_stock.api.exception.ErrorCode.MEMBER_NOT_FOUND;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto memberInfo(String memberKey){
        Member member = findByMemberKeyOrThrow(memberKey);
        return MemberDto.fromEntity(member);
    }

    public MemberDto memberEdit(MemberEditRequest request, String memberKey){
        Member member = findByMemberKeyOrThrow(memberKey);
        member.updateMember(request);
        return MemberDto.fromEntity(member);
    }

    private Member findByMemberKeyOrThrow(String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}
