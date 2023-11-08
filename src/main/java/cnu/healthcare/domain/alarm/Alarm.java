package cnu.healthcare.domain.alarm;

import cnu.healthcare.domain.group.MemberGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long alarmId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mg_id")
    private MemberGroup memberGroup;

    private String alarmName;

    private LocalTime time;

    @OneToOne
    @JoinColumn(name = "voice_id")
    private Voice voice;

    @Lob
    private List<String> days;

    @Builder
    public Alarm(MemberGroup memberGroup, String alarmName, LocalTime time, Voice voice, List<String> days) {
        this.memberGroup = memberGroup;
        this.alarmName = alarmName;
        this.time = time;
        this.voice = voice;
        this.days = days;
    }
}
