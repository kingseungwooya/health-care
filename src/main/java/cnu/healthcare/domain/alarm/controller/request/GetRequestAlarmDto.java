package cnu.healthcare.domain.alarm.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRequestAlarmDto {
    private String memberId;
    private String groupId;
    private String day;
}

