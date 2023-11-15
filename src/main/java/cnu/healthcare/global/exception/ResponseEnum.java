package cnu.healthcare.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    // AUTH_BAD_REQUEST(403, "bad request"),
    // AUTH_INVALID_TOKEN(401, "invalid token"),
    // AUTH_NOT_JOINED(405, "not joined user"),
    // AUTH_REFRESH_DOES_NOT_EXIST(401, "REFRESH_DOES_NOT_EXIST"),
    // AUTH_REFRESH_EXPIRED(401, "AUTH_REFRESH_EXPIRED"),

    ALARM_ERROR(403, "alarm already successed"),

    VOICE_INVALID_TYPE(401, "invalid extension for voice"),

    GROUP_CODE_NOT_EXIST(401, "invalid group code"),
    GROUP_ALREADY_JOINED(405, "group already joined"),

    USER_JOIN_SUCCESS(200, "회원가입에 성공하였습니다."),
    USER_JOIN_DUPLICATE(400, "이미 존재하는 아이디 입니다.");


    private final int code;
    private final String message;

}