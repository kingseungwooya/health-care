package cnu.healthcare.domain.group;

import cnu.healthcare.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class MemberGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_id")
    private Long memberGroupId;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    public MemberGroup(Member member, Group group) {
        this.member = member;
        this.group = group;
    }
}
