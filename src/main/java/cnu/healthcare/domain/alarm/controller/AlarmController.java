package cnu.healthcare.domain.alarm.controller;

import cnu.healthcare.domain.alarm.controller.request.AlarmDto;
import cnu.healthcare.domain.alarm.controller.request.GetRequestAlarmDto;
import cnu.healthcare.domain.alarm.controller.response.AlarmResponseDto;
import cnu.healthcare.domain.alarm.service.AlarmService;
import cnu.healthcare.domain.alarm.service.FileHandler;
import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import cnu.healthcare.global.exception.ResponseEnum;
import cnu.healthcare.global.exception.handler.CustomApiException;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.*;
import java.io.File;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static cnu.healthcare.domain.alarm.controller.AlarmController.REST_URL_ALARM;

@RequiredArgsConstructor
@RestController
@RequestMapping(REST_URL_ALARM)
public class AlarmController {

    public static final String REST_URL_ALARM = "api/mvp/user/alarm";

    private final AlarmService alarmService;
    // 알람 생성 기능 ㅇ
    // 알람 푸시 기능
    // 녹음 기능 ㅇ
    // 게획 완료시 완료료 바뀌는 기능 ㅇ


    @PostMapping("")
    @ApiOperation(value = "알람 생성" , notes = ""
            + "name = \"groupId\", value = \"그룹 id\"),\n"
            + "name = \"memberId\", value = \"알람을 설정된 사람의 id\"),\n"
            + "name = \"days\", value = \"설정 요일 배열 MON, TUES, WED, THURS, FRI, SAT, SUN 고정 \"),\n"
            + "name = \"time\", value = \"알람 설정 시간 형식 H:mm:ss \"),\n"
            + "name = \"voice\", value = \"multipart/form-data 요청필요\"),\n"
            + "name = \"alarmName\", value = \"계획명\")"
            + "")
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
    @PostMapping("/list")
    @ApiOperation( value = "사용자와 Group에 따른 알람 갖고오기")
    @ApiResponses({
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 400, message = "wrong request"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<List<AlarmResponseDto>> getAlarm(@RequestBody GetRequestAlarmDto getRequestAlarmDto) {
        return ResponseEntity.ok().body(alarmService.getAlarm(getRequestAlarmDto));
    }

    @PostMapping("/test")
    @ApiOperation(value = "알람 생성")
    @ApiResponses({
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 400, message = "wrong request"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<test> createAlarmTest(
            @RequestParam(value = "voice", required = false) MultipartFile voice
    )  {
        // System.out.println(voice.getOriginalFilename());
        FileHandler fileHandler = new FileHandler();
        try {
            fileHandler.parseFileInfo(voice);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            throw new CustomApiException(ResponseEnum.VOICE_INVALID_TYPE);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new test(voice.getOriginalFilename()));
    }

    @GetMapping("/success/{alarmId}")
    @ApiOperation(value = "알람 확인 후 완료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "voice를 찾을 수 없음"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<Void> success(@PathVariable Long alarmId) {
        alarmService.success(alarmId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/voice/{alarmId}")
    public ResponseEntity<Resource> downLoadFile(@PathVariable Long alarmId) {
        File file = alarmService.getVoice(alarmId);
        if(file.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +file.getName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(file));
        }
        return ResponseEntity.notFound().build();

    }
}

