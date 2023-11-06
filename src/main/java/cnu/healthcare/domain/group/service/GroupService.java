package cnu.healthcare.domain.group.service;

import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface GroupService {
    GroupCodeRespDto createGroup(GroupDto groupDto);
}
