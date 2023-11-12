package cnu.healthcare.domain.alarm.repo;

import cnu.healthcare.domain.alarm.Voice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceRepository extends JpaRepository<Voice, Long> {
}
