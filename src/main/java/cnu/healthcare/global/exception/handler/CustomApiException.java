package cnu.healthcare.global.exception.handler;

import cnu.healthcare.global.exception.ResponseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomApiException extends RuntimeException{

    private final ResponseEnum responseEnum;

}