package cnu.healthcare.domain.alarm.service;

import cnu.healthcare.domain.alarm.Alarm;
import cnu.healthcare.domain.alarm.Voice;
import cnu.healthcare.domain.alarm.controller.request.AlarmDto;
import cnu.healthcare.domain.alarm.controller.request.GetRequestAlarmDto;
import cnu.healthcare.domain.alarm.controller.response.AlarmResponseDto;
import cnu.healthcare.domain.alarm.repo.AlarmRepository;
import cnu.healthcare.domain.alarm.repo.VoiceRepository;
import cnu.healthcare.domain.group.MemberGroup;
import cnu.healthcare.domain.group.repo.GroupRepository;
import cnu.healthcare.domain.group.repo.MemberGroupRepository;
import cnu.healthcare.domain.member.repo.MemberRepository;
import cnu.healthcare.global.exception.ResponseEnum;
import cnu.healthcare.global.exception.handler.CustomApiException;
import java.util.List;
import java.util.stream.Collectors;
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
    private FileHandler fileHandler = new FileHandler();

    @Override
    public void createAlarm(AlarmDto alarmDto) {
        Voice voice;
        try {
            // System.out.println(alarmDto.getVoice().getOriginalFilename());
            voice = fileHandler.parseFileInfo(alarmDto.getVoice());
            voice = voiceRepository.save(voice);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            throw new CustomApiException(ResponseEnum.VOICE_INVALID_TYPE);
        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm:ss");

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

    @Override
    public List<AlarmResponseDto> getAlarm(GetRequestAlarmDto getRequestAlarmDto) {
        MemberGroup mg = memberGroupRepository.findByGroupAndMember(
                groupRepository.findById(UUID.fromString(getRequestAlarmDto.getGroupId())).get(),
                memberRepository.findByMemberId(getRequestAlarmDto.getMemberId())
        );
        List<Alarm> alarms = alarmRepository.findByMemberGroup(mg).stream()
                .filter(
                        a -> a.getDays().contains(getRequestAlarmDto.getDay())
                ).collect(Collectors.toList());

        return alarms.stream()
                .map(
                        a -> new AlarmResponseDto(a.getAlarmName(), a.getTime(), a.isSuccess())
                ).collect(Collectors.toList());
    }
}
