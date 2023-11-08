package cnu.healthcare.domain.group.service;

import cnu.healthcare.domain.group.Group;
import cnu.healthcare.domain.group.MemberGroup;
import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.request.JoinGroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import cnu.healthcare.domain.group.controller.dto.response.MyGroupDto;
import cnu.healthcare.domain.group.repo.GroupRepository;
import cnu.healthcare.domain.group.repo.MemberGroupRepository;
import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import cnu.healthcare.global.exception.ResponseEnum;
import cnu.healthcare.global.exception.handler.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Transactional
    @Override
    public void joinGroup(JoinGroupDto joinGroupDto) {
        try {
            UUID.fromString(joinGroupDto.getGroupCode());
        } catch (IllegalArgumentException e) {
            throw new CustomApiException(ResponseEnum.GROUP_CODE_NOT_EXIST);
        }
        Group group = groupRepository.findById(UUID.fromString(joinGroupDto.getGroupCode()))
                .orElseThrow(
                        () -> new CustomApiException(ResponseEnum.GROUP_CODE_NOT_EXIST)
                );
        Member member = memberRepository.findByMemberId(joinGroupDto.getMemberId());

        if (memberGroupRepository.existsByGroupAndMember(group, member)) {
            throw new CustomApiException(ResponseEnum.GROUP_ALREADY_JOINED);
        }

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();
        memberGroupRepository.save(memberGroup);
    }

    @Override
    public List<MyGroupDto> getMyGroups(String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByMember(member);
        // member로 memberGroup모두 찾기
        List<Group> myGroups = memberGroups.stream()
                .map(
                        m -> m.getGroup()
                ).collect(Collectors.toList());

        return myGroups.stream()
                .map(
                        g -> new MyGroupDto(g,
                                memberGroupRepository.findAllByGroup(g).stream()
                                        .map(
                                                mg -> mg.getMember()
                                        ).collect(Collectors.toList())
                        )

                ).collect(Collectors.toList());
    }

}


