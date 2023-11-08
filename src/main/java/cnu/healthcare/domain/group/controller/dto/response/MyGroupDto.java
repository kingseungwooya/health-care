package cnu.healthcare.domain.group.controller.dto.response;

import cnu.healthcare.domain.group.Group;
import cnu.healthcare.domain.member.Member;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyGroupDto {
    private final String groupCode;
    private final String groupName;
    private final List<String> members;

    public MyGroupDto(Group group, List<Member> members) {
        this.groupName = group.getGroupName();
        this.groupCode = group.getGroupId().toString();
        this.members = members.stream()
                .map(
                        m -> m.getMemberName()
                ).collect(Collectors.toList());
    }
}
