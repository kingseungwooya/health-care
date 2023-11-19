package cnu.healthcare.domain.fcm.service;

import cnu.healthcare.domain.fcm.controller.request.FCMNotificationRequestDto;
import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import cnu.healthcare.global.exception.ResponseEnum;
import cnu.healthcare.global.exception.handler.CustomApiException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;

    public String sendNotificationByToken(FCMNotificationRequestDto requestDto) {

        Member member = memberRepository.findByMemberId(requestDto.getTargetMemberId());

        if (member.getDeviceKey() != null) {

            // 여기다가
            Notification notification = Notification.builder()
                    .setTitle(requestDto.getTitle())
                    .setBody(requestDto.getBody())
                    // .setImage(requestDto.getImage())
                    .build();


            Message message = Message.builder()
                    .setToken(member.getDeviceKey())
                    .setNotification(notification)
                    // .putAllData(requestDto.getData())
                    .build();

            try {
                firebaseMessaging.send(message);
                return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.getTargetMemberId();
            } catch (FirebaseMessagingException e) {
                return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.getTargetMemberId();
            }
        } else {
            return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다. targetUserId=" + requestDto.getTargetMemberId();
        }



    }
}
