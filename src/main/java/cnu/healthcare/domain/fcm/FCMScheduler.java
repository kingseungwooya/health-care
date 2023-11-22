package cnu.healthcare.domain.fcm;

import cnu.healthcare.domain.alarm.Alarm;
import cnu.healthcare.domain.alarm.repo.AlarmRepository;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FCMScheduler {

    private static final Logger logger = LoggerFactory.getLogger(FCMScheduler.class);

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Scheduled(cron = "0 * * * * *")
    public void fcmLoader() {
        List<Alarm> alarms = alarmRepository.findByTime(LocalTime.now()
                .withSecond(0)
                .withNano(0));
        logger.info("scheduler 반복");
        if (alarms.isEmpty()) {
            return;
        }
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        logger.info("불러온 alarm들 갯수 아직 요일을 적용하진 않음 " + alarms.size());
        logger.info(findDay(dayOfWeek));
        List<Alarm> filteredAlarms = alarms.stream().filter(
                a -> a.getDays().contains(findDay(dayOfWeek))
        ).collect(Collectors.toList());
        if (filteredAlarms.isEmpty()) {
            return;
        }
        for (Alarm alarm : filteredAlarms) {
            logger.info(alarm.getAlarmName() + "이 감지됌");
            // 여기다가
            String body = alarm.getMemberGroup()
                    .getMember()
                    .getMemberName()
                     + "님 알람시간이 되었습니다. 클릭해 전달된 목소리를 들어보세요";
            Notification notification = Notification.builder()
                    .setTitle(alarm.getAlarmName())
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(alarm.getMemberGroup().getMember().getDeviceKey())
                    .setNotification(notification)
                    .putData("alarmTime", alarm.getTime().toString())
                    .putData("alarmId", alarm.getAlarmId().toString())
                    .build();

            try {
                firebaseMessaging.send(message);
                logger.info("message send success");
            } catch (FirebaseMessagingException e) {
                logger.error("message send fail -> {}", e.getMessage());
            }
        }
    }

    /**
     * MON, TUES, WED, THURS, FRI, SAT, SUN
     * @param dayOfWeek
     * @return
     */
    private String findDay(DayOfWeek dayOfWeek) {
        switch (dayOfWeek.getValue()) {
            case 1:
                return "MON";
            case 2:
                return "TUES";
            case 3:
                return "WED";
            case 4:
                return "THURS";
            case 5:
                return "FRI";
            case 6:
                return "SAT";
            case 7:
                return "SUN";
            default:
                return "null";
        }
    }



}
