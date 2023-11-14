package cnu.healthcare.domain.alarm.service;

import cnu.healthcare.domain.alarm.Voice;
import cnu.healthcare.global.exception.ResponseEnum;
import cnu.healthcare.global.exception.handler.CustomApiException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class FileHandler {
    public Voice parseFileInfo(
            MultipartFile multipartFile
    ) throws Exception {
        // System.out.println("진입");
        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
        // System.out.println("current date "  + current_date);
        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "\\";

        // 경로를 지정하고 그곳에다가 저장할 심산이다
        String path = "voice/" + current_date;
        File file = new File(path);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if (!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }
        // System.out.println("abs path" + path);

        // 파일들을 이제 만져볼 것이다
        if (!multipartFile.isEmpty()) {
            // jpeg, png, gif 파일들만 받아서 처리할 예정
            String contentType = multipartFile.getContentType();
            String originalFileExtension = " ";
            // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
            // System.out.println("content type" + contentType);
            if (ObjectUtils.isEmpty(contentType)) {
                throw new CustomApiException(ResponseEnum.VOICE_INVALID_TYPE);
            } else {
                if (contentType.contains("audio/wav")) {
                    originalFileExtension = ".wav";
                } else if (contentType.contains("audio/mp3")) {
                    originalFileExtension = ".mp3";
                }
                // 다른 파일 명이면 아무 일 하지 않는다
                else {
                    throw new CustomApiException(ResponseEnum.VOICE_INVALID_TYPE);
                }
            }
            // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
            String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
            // 생성 후 리스트에 추가
            Voice voice = Voice.builder()
                    .fileSize(multipartFile.getSize())
                    .originalFileName(multipartFile.getOriginalFilename())
                    .storedFilePath(path + "/" + new_file_name)
                    .build();


            // 저장된 파일로 변경하여 이를 보여주기 위함
            // file = new File(  path + "/" + new_file_name);
            String fullFilePath = path + "/" + new_file_name;
            // System.out.println("여기꺼진 된거면 거의 다 끝난건디..");
            // System.out.println(voice.getStoredFilePath());
            // System.out.println(file.getAbsolutePath());
            // System.out.println(file.getPath());
            Path filepath = Paths.get(fullFilePath).toAbsolutePath();
            multipartFile.transferTo(filepath);
            // System.out.println("----- 끝이라니꼐");
            return voice;
        }

        throw new CustomApiException(ResponseEnum.VOICE_INVALID_TYPE);
    }
}