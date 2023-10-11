package cnu.healthcare.domain.member.service;

import cnu.healthcare.domain.member.Role;
import cnu.healthcare.domain.member.controller.dto.request.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void register(RegisterDto registerDto);
}
