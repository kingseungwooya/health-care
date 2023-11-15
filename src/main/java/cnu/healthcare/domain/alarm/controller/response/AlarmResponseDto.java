package cnu.healthcare.domain.alarm.controller.response;

import java.time.LocalTime;
import lombok.Getter;

@Getter
public class AlarmResponseDto {
    private final Long alarmId;
    private final String alarmName;
    private final LocalTime time;
    private final boolean status;

    public AlarmResponseDto(Long alarmId, String alarmName, LocalTime time, boolean status) {
        this.alarmId = alarmId;
        this.alarmName = alarmName;
        this.time = time;
        this.status = status;
    }
}
