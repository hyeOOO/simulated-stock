package toyproject.simulated_stock.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.simulated_stock.domain.member.entity.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String memberKey;  // 추가
    private String name;
    private String email;
    private String profile;
    private String nickname;   // 추가 (필요한 경우)

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .memberKey(member.getMemberKey())  // 추가
                .name(member.getName())
                .email(member.getEmail())
                .profile(member.getProfile())
                .build();
    }
}