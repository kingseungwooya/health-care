package cnu.healthcare.domain.group.controller.dto.response;

import lombok.Getter;

@Getter
public class GroupInfoDto {
    private final String memberName;
    private final String memberId;

    public GroupInfoDto(String memberName, String memberId) {
        this.memberName = memberName;
        this.memberId = memberId;
    }
}
