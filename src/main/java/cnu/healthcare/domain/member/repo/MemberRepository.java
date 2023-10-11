package cnu.healthcare.domain.member.repo;

import cnu.healthcare.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Member findByMemberId(String s);
}
