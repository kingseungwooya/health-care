package cnu.healthcare.domain.alarm.controller.response;

import java.time.LocalTime;
import lombok.Getter;

@Getter
public class AlarmResponseDto {
    private final String alarmName;
    private final LocalTime time;
    private final boolean status;

    public AlarmResponseDto(String alarmName, LocalTime time, boolean status) {
        this.alarmName = alarmName;
        this.time = time;
        this.status = status;
    }
}
