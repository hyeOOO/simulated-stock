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
    private String name;
    private String email;
    private String profile;

    public static MemberDto fromEntity(toyproject.simulated_stock.domain.member.entity.Member member){
        MemberDto memberDto = MemberDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .profile(member.getProfile())
                .build();

        return memberDto;
    }
}
