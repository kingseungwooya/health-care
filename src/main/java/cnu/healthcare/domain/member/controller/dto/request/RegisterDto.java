package cnu.healthcare.domain.member.controller.dto.request;

import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.Role;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String memberId;

    private String password;

    private String memberName;

    public Member toMember(String password, Role role) {
        return Member.builder()
                .memberId(memberId)
                .memberName(memberName)
                .password(password)
                .roles(List.of(role))
                .build();
    }



}
