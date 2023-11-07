package cnu.healthcare.domain.group.service;

import cnu.healthcare.domain.group.Group;
import cnu.healthcare.domain.group.MemberGroup;
import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import cnu.healthcare.domain.group.repo.GroupRepository;
import cnu.healthcare.domain.group.repo.MemberGroupRepository;
import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public GroupCodeRespDto createGroup(GroupDto groupDto) {
        Member member = memberRepository.findByMemberId(groupDto.getMemberId());
        Group group = groupRepository.save(
                new Group(groupDto.getGroupName())
        );

        MemberGroup memberGroup = MemberGroup.builder()
                .group(group)
                .member(member)
                .build();
        memberGroupRepository.save(memberGroup);
        return new GroupCodeRespDto(group.getGroupId().toString());
    }

}
