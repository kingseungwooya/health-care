package cnu.healthcare.domain.member.service;

import cnu.healthcare.domain.member.Role;
import cnu.healthcare.domain.member.RoleName;
import cnu.healthcare.domain.member.controller.dto.request.RegisterDto;
import cnu.healthcare.domain.member.repo.MemberRepository;
import cnu.healthcare.domain.member.repo.RoleRepository;

import javax.transaction.Transactional;

import cnu.healthcare.global.exception.ResponseEnum;
import cnu.healthcare.global.exception.handler.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final static Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);


    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    @Override
    public void register(RegisterDto registerDto) {
        String encodedPw = passwordEncoder.encode(registerDto.getPassword());
        if (memberRepository.existsById(registerDto.getMemberId())) {
            throw new CustomApiException(ResponseEnum.USER_JOIN_DUPLICATE);
        }
        Role role = roleRepository.findByName(RoleName.ROLE_USER);
        memberRepository.save(registerDto.toMember(encodedPw, role));
    }

    @Override
    public void saveRole() {
        roleRepository.save(new Role((RoleName.ROLE_USER)));
        roleRepository.save(new Role((RoleName.ROLE_ADMIN)));
    }


}
