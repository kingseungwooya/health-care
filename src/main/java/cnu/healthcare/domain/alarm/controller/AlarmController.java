package cnu.healthcare.domain.alarm.controller;

import cnu.healthcare.domain.alarm.controller.request.AlarmDto;
import cnu.healthcare.domain.alarm.service.AlarmService;
import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static cnu.healthcare.domain.alarm.controller.AlarmController.REST_URL_ALARM;

@RequiredArgsConstructor
@RestController
@RequestMapping(REST_URL_ALARM)
public class AlarmController {

    public static final String REST_URL_ALARM = "api/mvp/alarm";

    private final AlarmService alarmService;
    // 알람 생성 기능
    // 알람 푸시 기능
    // 녹음 기능
    // 게획 완료시 완료료 바뀌는 기능


    @PostMapping("")
    @ApiOperation(value = "알람 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "그룹 id"),
            @ApiImplicitParam(name = "memberId", value = "알람을 설정된 사람의 id"),
            @ApiImplicitParam(name = "days", value = "설정 요일 배열 MON, TUES, WED, THURS, FRI, SAT, SUN 고정 "),
            @ApiImplicitParam(name = "time", value = "알람 설정 시간 형식 H:mm:ss "),
            @ApiImplicitParam(name = "voice", value = "multipart/form-data 요청필요"),
            @ApiImplicitParam(name = "alarmName", value = "계획명")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 400, message = "wrong request"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<Void> createAlarm(
            @RequestParam(value = "voice", required = false) MultipartFile voice,
            @RequestParam("memberId") String memberId,
            @RequestParam("groupId") String groupId,
            @RequestParam("days") List<String> days,
            @RequestParam("time") String time,
            @RequestParam("alarmName") String alarmName
            ) {
        alarmService.createAlarm(
                new AlarmDto(groupId, memberId, alarmName, days, time, voice)
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
