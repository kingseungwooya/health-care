package cnu.healthcare.domain.group.controller;

import cnu.healthcare.domain.group.controller.dto.request.GroupDto;
import cnu.healthcare.domain.group.controller.dto.request.JoinGroupDto;
import cnu.healthcare.domain.group.controller.dto.response.GroupCodeRespDto;
import cnu.healthcare.domain.group.controller.dto.response.MyGroupDto;
import cnu.healthcare.domain.group.service.GroupService;
import cnu.healthcare.domain.member.controller.dto.request.RegisterDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cnu.healthcare.domain.group.controller.GroupController.REST_URL_GROUP;

@RequiredArgsConstructor
@RestController
@RequestMapping(REST_URL_GROUP)
public class GroupController {

    private final GroupService groupService;
    public static final String REST_URL_GROUP = "api/mvp/group";

    @PostMapping("")
    @ApiOperation(value = "그룹 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "member 고유한 id"),
            @ApiImplicitParam(name = "groupName", value = "Group name")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 400, message = "wrong request"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<GroupCodeRespDto> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(groupDto));
    }

    @PostMapping("/join")
    @ApiOperation(value = "참여 코드로 그룹 참가")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "member 고유한 id"),
            @ApiImplicitParam(name = "groupCode", value = "group code")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "success"),
            @ApiResponse(code = 401, message = "invalid group code"),
            @ApiResponse(code = 405, message = "group already joined"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<Void> joinGroup(@RequestBody JoinGroupDto joinGroupDto) {
        groupService.joinGroup(joinGroupDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{memberId}")
    @ApiOperation(value = "참여 그룹들 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "member 고유한 id"),
            @ApiImplicitParam(name = "groupCode", value = "group code")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "success"),
            @ApiResponse(code = 401, message = "invalid group code"),
            @ApiResponse(code = 405, message = "group already joined"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<List<MyGroupDto>> getMyGroup(@PathVariable String memberId){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getMyGroups(memberId));
    }


}
