package cnu.healthcare.domain.group.repo;

import cnu.healthcare.domain.group.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {
}
