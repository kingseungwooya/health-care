package cnu.healthcare.domain.alarm.service;

import cnu.healthcare.domain.alarm.Alarm;
import cnu.healthcare.domain.alarm.Voice;
import cnu.healthcare.domain.alarm.controller.request.AlarmDto;
import cnu.healthcare.domain.alarm.repo.AlarmRepository;
import cnu.healthcare.domain.alarm.repo.VoiceRepository;
import cnu.healthcare.domain.group.MemberGroup;
import cnu.healthcare.domain.group.repo.GroupRepository;
import cnu.healthcare.domain.group.repo.MemberGroupRepository;
import cnu.healthcare.domain.member.repo.MemberRepository;
import cnu.healthcare.global.exception.ResponseEnum;
import cnu.healthcare.global.exception.handler.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmServiceImpl implements AlarmService {

    private final MemberGroupRepository memberGroupRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;
    private final VoiceRepository voiceRepository;
    private FileHandler fileHandler;

    @Override
    public void createAlarm(AlarmDto alarmDto) {
        Voice voice;
        try {
            voice = fileHandler.parseFileInfo(alarmDto.getVoice());
            voiceRepository.save(voice);
        } catch (Exception e) {
            throw new CustomApiException(ResponseEnum.VOICE_INVALID_TYPE);
        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H::mm:ss");

        MemberGroup memberGroup = memberGroupRepository.findByGroupAndMember(
                groupRepository.findById(UUID.fromString(alarmDto.getGroupId())).get(),
                memberRepository.findByMemberId(alarmDto.getMemberId())
        );


        Alarm alarm = Alarm.builder()
                .alarmName(alarmDto.getAlarmName())
                .time(LocalTime.parse(alarmDto.getTime(), timeFormatter))
                .voice(voice)
                .memberGroup(memberGroup)
                .days(alarmDto.getDays())
                .build();
        alarmRepository.save(alarm);

    }
}
