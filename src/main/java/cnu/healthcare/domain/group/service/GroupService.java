package cnu.healthcare.domain.group.service;

import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.request.JoinGroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupInfoDto;
import cnu.healthcare.domain.group.controller.dto.response.MyGroupDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface GroupService {
    GroupCodeRespDto createGroup(GroupDto groupDto);

    void joinGroup(JoinGroupDto joinGroupDto);

    List<MyGroupDto> getMyGroups(String memberId);

    List<GroupInfoDto> getGroupInfo(String groupId);
}
