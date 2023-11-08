package cnu.healthcare.domain.group.controller.dto.response;

import cnu.healthcare.domain.member.Member;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyGroupDto {
    private final String GroupName;
    private final List<String> members;

    public MyGroupDto(String groupName, List<Member> members) {
        GroupName = groupName;
        this.members = members.stream()
                .map(
                        m -> m.getMemberName()
                ).collect(Collectors.toList());
    }
}
