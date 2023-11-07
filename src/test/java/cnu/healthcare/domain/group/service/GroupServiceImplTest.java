package cnu.healthcare.domain.group.service;

import cnu.healthcare.domain.group.Group;
import cnu.healthcare.domain.group.MemberGroup;
import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.request.JoinGroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import cnu.healthcare.domain.group.repo.GroupRepository;
import cnu.healthcare.domain.group.repo.MemberGroupRepository;
import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import cnu.healthcare.global.exception.handler.CustomApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = true)
public class GroupServiceImplTest {

    @Autowired
    private GroupService groupService;
    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    Member member;
    @BeforeEach
    public void createInstance() {
        member = Member.builder()
                .memberId("test")
                .build();
        memberRepository.save(member);
    }

    @Test
    public void testCreateGroup() {
        // given
        GroupDto groupDto = new GroupDto();
        groupDto.setMemberId("test");
        groupDto.setGroupName("Test Group");

        // when
        GroupCodeRespDto respDto = groupService.createGroup(groupDto);
        UUID groupCode = UUID.fromString(respDto.getGroupCode());

        Group group = groupRepository.findById(groupCode).get();
        MemberGroup memberGroup = memberGroupRepository.findByGroupAndMember(group, member);

        // then
        assertEquals(member.getMemberId(), memberGroup.getMember().getMemberId()
        );
    }

    @Test
    public void GROUP_JOIN() {
        // given
        GroupDto groupDto = new GroupDto();
        groupDto.setMemberId("test");
        groupDto.setGroupName("Test Group");
        GroupCodeRespDto respDto = groupService.createGroup(groupDto);

        // when
        Member newMember = Member.builder()
                .memberId("test2")
                .build();
        memberRepository.save(newMember);

        // then
        JoinGroupDto alreadyJoinMember = new JoinGroupDto(respDto.getGroupCode(), member.getMemberId());
        assertThrows(CustomApiException.class,
                () -> groupService.joinGroup(alreadyJoinMember)
        );
        JoinGroupDto invalidGroupCode = new JoinGroupDto("invalid-UUID", newMember.getMemberId());
        assertThrows(CustomApiException.class,
                () -> groupService.joinGroup(invalidGroupCode)
        );
        JoinGroupDto correctJoin = new JoinGroupDto(respDto.getGroupCode(), newMember.getMemberId());
        groupService.joinGroup(correctJoin);

        Group group = groupRepository.findById(UUID.fromString(respDto.getGroupCode())).get();
        MemberGroup newMemberGroup = memberGroupRepository.findByGroupAndMember(group, newMember);
        System.out.println(newMemberGroup.getMember().getMemberId());
        assertThat(memberGroupRepository.existsByGroupAndMember(group, newMember), is(true));
    }

    // @Test
    // public void UUID_GROUP_TEST() {
    //     Group group = groupRepository.save(new Group("test"));
    //     System.out.println(group.getGroupId());
    // }
}
