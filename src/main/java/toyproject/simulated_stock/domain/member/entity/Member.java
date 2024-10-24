package toyproject.simulated_stock.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.simulated_stock.api.member.dto.MemberEditRequest;

import javax.xml.stream.events.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false, unique = true)
    private String memberKey;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column
    private String comment;

    @Builder
    public Member(String name, String email, String profile, String memberKey, Role role, String comment) {
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.memberKey = memberKey;
        this.role = role;
    }

    public void updateMember(MemberEditRequest request) {
        this.name = request.name();
        this.comment = request.comment();
    }

}
