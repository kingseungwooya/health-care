package cnu.healthcare.domain.member.controller;

import static cnu.healthcare.domain.member.controller.MemberController.REST_URL_MEMBER;

import cnu.healthcare.domain.member.controller.dto.request.RegisterDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(REST_URL_MEMBER)
public class MemberController {
    public static final String REST_URL_MEMBER = "api/mvp/member";

    @PostMapping("/register")
    @ApiOperation(value = "회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "member 고유한 id"),
            @ApiImplicitParam(name = "memberName", value = "member 닉네임")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "wrong request"),
            @ApiResponse(code = 500, message = "server error")
    })
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        return null;
    }
}
