package cnu.healthcare.domain.fcm.controller.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FCMNotificationRequestDto {
    private String targetMemberId;
    private String title;
    private String body;
    private String alarmId;
    // private String image;
    // private Map<String, String> data;

}
