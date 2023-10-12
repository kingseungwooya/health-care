package cnu.healthcare.domain.member.controller;

import static cnu.healthcare.domain.member.controller.MemberController.REST_URL_MEMBER;

import cnu.healthcare.domain.member.controller.dto.request.RegisterDto;
import cnu.healthcare.domain.member.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping(REST_URL_MEMBER)
public class MemberController {
    public static final String REST_URL_MEMBER = "api/mvp/member";

    private final MemberService memberService;

    @PostMapping("/register")
    @ApiOperation(value = "회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "member 고유한 id"),
            @ApiImplicitParam(name = "memberName", value = "member 닉네임"),
            @ApiImplicitParam(name = "password", value = "member 비번")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "wrong request"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) {
        memberService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * test 용
     */
    @ApiIgnore
    @PostMapping("/init-role")
    public void initRole() {
        memberService.saveRole();
    }
}
