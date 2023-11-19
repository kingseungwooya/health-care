package cnu.healthcare.domain.member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_name")
    private String memberName;

    private String password;

    @Column(name = "device_key")
    private String deviceKey;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @Builder
    public Member(String memberId, String memberName, String password, Collection<Role> roles, String deviceKey) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.roles = roles;
        this.deviceKey = deviceKey;
    }

}
