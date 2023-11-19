package cnu.healthcare.domain.fcm.service;

import cnu.healthcare.domain.fcm.controller.request.FCMNotificationRequestDto;
import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
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
    private final String tempFirebaseKey = "f3UV2f80RWKitgYPgQd7Qi:APA91bEGMna4znUYJKFlaJLmkuLvzdjgeFky4GIstwJzL7pSa8xdJCdPUT9J9qSJuYWhZknVfhJq-s4cieaCJEsd0Pj46Ced1lr05RgXoMTlLJU0LWnFF4kRJGTlaO7Xl276vk2bu9i3";
    public String sendNotificationByToken(FCMNotificationRequestDto requestDto) {

        //Optional<Member> member = MemberRepository.findByMemberId(requestDto.getTargetUserId());

        //if (user.isPresent()) {
        //if (member.isPresent())
            //if (user.get().getFirebaseToken() != null) {
            //테이블 수정 후 tempfirebasekey코드 삭제후 위 코드 활성
            if (tempFirebaseKey != null) {

                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(requestDto.getBody())
                        // .setImage(requestDto.getImage())
                        .build();


                Message message = Message.builder()
                        //.setToken(user.get().getFirebaseToken())
                        //테이블 수정 후 tempfirebasekey코드 삭제후 위 코드 활성
                        .setToken(tempFirebaseKey)
                        .setNotification(notification)
                        // .putAllData(requestDto.getData())
                        .build();

                try {
                    firebaseMessaging.send(message);
                    return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.getTargetUserId();
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                    return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.getTargetUserId();
                }
            } else {
                return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
            }

//        } else {
//            return "해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
//        }


    }
}
