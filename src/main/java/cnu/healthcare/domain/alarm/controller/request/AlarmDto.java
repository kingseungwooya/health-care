package cnu.healthcare.domain.alarm.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {
    private String groupId;
    private String memberId;
    private String alarmName;
    // " MON, TUES, WED, THURS, FRI, SAT, SUN
    private List<String> days;
    // "" hh::mm ""
    private String time;
    private MultipartFile voice;
}
