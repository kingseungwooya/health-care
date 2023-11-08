package cnu.healthcare.domain.group.repo;

import cnu.healthcare.domain.group.Group;
import cnu.healthcare.domain.group.MemberGroup;
import cnu.healthcare.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {
    MemberGroup findByGroupAndMember(Group group, Member member);
    boolean existsByGroupAndMember(Group group, Member member);
    List<MemberGroup> findAllByMember(Member member);
    List<MemberGroup> findAllByGroup(Group group);
}
