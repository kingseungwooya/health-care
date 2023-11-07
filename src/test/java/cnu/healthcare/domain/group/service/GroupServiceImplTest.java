package cnu.healthcare.domain.group.service;

import cnu.healthcare.domain.group.Group;
import cnu.healthcare.domain.group.MemberGroup;
import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import cnu.healthcare.domain.group.repo.GroupRepository;
import cnu.healthcare.domain.group.repo.MemberGroupRepository;
import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @Test
    public void testCreateGroup() {
        // given
        Member member = Member.builder()
                .memberId("test")
                .build();
        memberRepository.save(member);

        GroupDto groupDto = new GroupDto();
        groupDto.setMemberId("test");
        groupDto.setGroupName("Test Group");

        // when
        GroupCodeRespDto respDto = groupService.createGroup(groupDto);
        UUID groupCode = UUID.fromString(respDto.getGroupCode());

        Group group = groupRepository.findById(groupCode).get();
        MemberGroup memberGroup = memberGroupRepository.findByGroup(group);

        // 결과 확인
        assertEquals(member.getMemberId(), memberGroup.getMember().getMemberId()
        );
    }

    @Test
    public void UUID_GROUP_TEST() {
        Group group = groupRepository.save(new Group("test"));
        System.out.println(group.getGroupId());
    }
}
