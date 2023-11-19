package cnu.healthcare.domain.fcm.controller;

import cnu.healthcare.domain.fcm.controller.request.FCMNotificationRequestDto;
import cnu.healthcare.domain.fcm.service.FCMNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mvp/alarm/notify")
public class FCMNotificationApiController {

    private final FCMNotificationService fcmNotificationService;

    @PostMapping("")
    public ResponseEntity<String> sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(fcmNotificationService.sendNotificationByToken(requestDto));
    }
}
