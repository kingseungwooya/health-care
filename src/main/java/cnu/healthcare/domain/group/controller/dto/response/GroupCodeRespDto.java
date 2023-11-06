package cnu.healthcare.domain.group.controller.dto.response;

import lombok.Getter;

@Getter
public class GroupCodeRespDto {

    private final String groupCode;

    public GroupCodeRespDto(String groupCode) {
        this.groupCode = groupCode;
    }
}
