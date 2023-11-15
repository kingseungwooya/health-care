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

    private String days;

    @Column(name = "success_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isSuccess;

    @Builder
    public Alarm(MemberGroup memberGroup, String alarmName, LocalTime time, Voice voice, List<String> days) {
        this.memberGroup = memberGroup;
        this.alarmName = alarmName;
        this.time = time;
        this.voice = voice;
        this.days = arrayToString(days);
        this.isSuccess = false;
    }

    public void success () {
        this.isSuccess = true;
    }

    /**
     * 스케쥴러로 매일 초기화 필요
     */
    public void reset() {
        this.isSuccess = false;
    }

    private String arrayToString(List<String> days) {
        if (days == null || days.isEmpty()) {
            return ""; // 빈 배열이나 null이 들어오면 빈 문자열 반환
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < days.size(); i++) {
            result.append(days.get(i));
            if (i < days.size() - 1) {
                result.append(", "); // 마지막 요소가 아니면 쉼표와 공백 추가
            }
        }
        System.out.println(result.toString());

        return result.toString();
    }
}
