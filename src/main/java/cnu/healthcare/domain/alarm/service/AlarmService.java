package cnu.healthcare.domain.alarm.service;

import cnu.healthcare.domain.alarm.controller.request.AlarmDto;
import cnu.healthcare.domain.alarm.controller.request.GetRequestAlarmDto;
import cnu.healthcare.domain.alarm.controller.response.AlarmResponseDto;
import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AlarmService {

    void createAlarm(AlarmDto alarmDto);
    List<AlarmResponseDto> getAlarm(GetRequestAlarmDto getRequestAlarmDto);
    void success(Long alarmId);

    File getVoice(Long alarmId);
}
