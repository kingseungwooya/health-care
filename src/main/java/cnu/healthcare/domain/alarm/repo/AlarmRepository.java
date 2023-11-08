package cnu.healthcare.domain.alarm.repo;

import cnu.healthcare.domain.alarm.Alarm;
import cnu.healthcare.domain.alarm.Voice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
