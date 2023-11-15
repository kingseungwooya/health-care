package cnu.healthcare.domain.alarm.repo;

import cnu.healthcare.domain.alarm.Alarm;
import cnu.healthcare.domain.alarm.Voice;
import cnu.healthcare.domain.group.MemberGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByMemberGroup(MemberGroup mg);
}
