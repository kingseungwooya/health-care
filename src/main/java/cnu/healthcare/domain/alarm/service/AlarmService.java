package cnu.healthcare.domain.alarm.service;

import cnu.healthcare.domain.alarm.controller.request.AlarmDto;
import org.springframework.stereotype.Service;

@Service
public interface AlarmService {

    void createAlarm(AlarmDto alarmDto);
}
