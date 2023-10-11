package cnu.healthcare.domain.member.repo;

import cnu.healthcare.domain.member.Role;
import cnu.healthcare.domain.member.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);

}
